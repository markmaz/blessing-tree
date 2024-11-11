package com.blessingtree.controllers;

import com.blessingtree.dto.JwtRequest;
import com.blessingtree.dto.JwtResponse;
import com.blessingtree.components.JwtTokenUtil;
import com.blessingtree.dto.PasswordReminder;
import com.blessingtree.dto.PasswordReset;
import com.blessingtree.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.blessingtree.service.UserService;

@RestController
public class AuthController extends BaseController{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Long id = userService.getUserByUsername(authenticationRequest.getUsername()).getUserId();
        final String token = jwtTokenUtil.generateToken(userDetails, id, null);
        return org.springframework.http.ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/reminder")
    public ResponseEntity<?> sendPasswordReminder(@RequestBody PasswordReminder reminder){
        int status = passwordService.sendEmailReminder(reminder);

        return ResponseEntity.status(status).build();
    }


    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordReset passwordReset, HttpServletRequest request){
        int status = passwordService.resetPassword(passwordReset);
        return ResponseEntity.status(status).build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
    }

}

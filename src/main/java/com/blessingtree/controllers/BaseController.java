package com.blessingtree.controllers;

import com.blessingtree.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blessingtree.components.JwtTokenUtil;
import com.blessingtree.model.User;

@RestController
@RequestMapping("${api.version.prefix}")
public class BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    protected User getLoggedInUser(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return userService.findUserById(jwtTokenUtil.getUserIDFromToken(token));
    }
}

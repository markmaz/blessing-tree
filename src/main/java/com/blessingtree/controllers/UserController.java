package com.blessingtree.controllers;

import com.blessingtree.dto.UserDTO;
import com.blessingtree.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
public class UserController extends BaseController{
    private final UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }
    @PostMapping("/users")
    public UserDTO createUser(@RequestBody UserDTO userDTO, HttpServletRequest servletRequest){
        return this.userService.createUser(userDTO, getLoggedInUser(servletRequest));
    }

    @PatchMapping("/users/{id}")
    public UserDTO patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates, HttpServletRequest request){
        return this.userService.patchUpdateUser(id, updates, getLoggedInUser(request));
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDTO getAllUser(@PathVariable Long id, HttpServletRequest request){
        return this.userService.findDTOUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id, HttpServletRequest request){
        this.userService.deleteUser(id);
    }

}

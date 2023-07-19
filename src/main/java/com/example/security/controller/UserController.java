package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getActualUser() {
        //Do we really get the data via it?
        return ResponseEntity.ok(userService.getUserWithAuthorities().get());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersWithAdminProtected() {

        return ResponseEntity.ok(userService.getAllUsers());

    }
}

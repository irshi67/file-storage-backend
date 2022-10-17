package com.example.filetransfer.controller;

import com.example.filetransfer.model.User;
import com.example.filetransfer.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        userService.registerNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        User user1 = userService.loginUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }

    @DeleteMapping("/{email}/logout")
    public void logoutUser(@PathVariable String email){
        userService.logoutUser(email);
    }
}

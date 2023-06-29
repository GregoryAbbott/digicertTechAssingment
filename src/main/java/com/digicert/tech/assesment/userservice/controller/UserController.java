package com.digicert.tech.assesment.userservice.controller;

import com.digicert.tech.assesment.userservice.entity.User;
import com.digicert.tech.assesment.userservice.response.UserResponse;
import com.digicert.tech.assesment.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    private ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    private ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    private ResponseEntity<String> addUser(@RequestBody User newUser) {

        userService.addUser(newUser);
        /* Sending a simple string back here but could possibly have userService.addUser()
           return an appropriate message or body, could alter UserResponse object to have
           a message field and/or a status code and pass those back through an object */
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PutMapping("/users/{id}")
    private ResponseEntity<String> updateUser(@PathVariable("id") long id, @RequestBody User user) {

        boolean userFound = userService.updateUser(id, user);

        /* Using a simple boolean here but could possibly have userService.updateUser()
           return an appropriate message or body, could alter UserResponse object to have
           a message field and/or a status code and pass those back through an object */

        if (userFound) {
            return ResponseEntity.status(HttpStatus.OK).body("User updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/users/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable("id") long id) {

        boolean userDeleted = userService.deleteUser(id);
        //Sending a simple string back here but could possibly have userService.deleteUser()
        //return an appropriate message or body
        if (userDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

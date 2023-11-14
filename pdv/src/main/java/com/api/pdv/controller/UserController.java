package com.api.pdv.controller;

import com.api.pdv.util.UserMiddleware;
import com.api.pdv.model.User;
import com.api.pdv.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/list")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = this.userService.getUsers();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping( value = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        try {
            User u = this.userService.findUserById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping( value = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User user) {
            try {
                User u = this.userService.findUserById(id);
                if (u == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                } else {
                    Boolean isTrue = UserMiddleware.ValidUser(user.getName(), user.getPassword().trim());
                    if (isTrue) {
                        this.userService.updateUser(user, id);
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Inavalid/Camps");
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
    }

    @DeleteMapping( value = "/delete/{id}" )
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {
        try {
            User user = this.userService.findUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
            } else {
                this.userService.deleteUser(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("User/Deleted");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping( value = "/delete/all" )
    public ResponseEntity<String> deleteAllUsers() {
        try {
            this.userService.deleteAllUsers();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Users/Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

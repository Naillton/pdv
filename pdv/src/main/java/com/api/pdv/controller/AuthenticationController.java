package com.api.pdv.controller;

import com.api.pdv.dto.AuthenticationDTO;
import com.api.pdv.dto.UserDTO;
import com.api.pdv.util.UserMiddleware;
import com.api.pdv.model.User;
import com.api.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/cadastrar")
    public ResponseEntity<String> insertUser(@RequestBody UserDTO user) {
        User validUser = this.userService.loadUserByUsername(user.name());
        if (validUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Already/Exist");
        } else {
            Boolean isTrue = UserMiddleware.ValidUser(user.name(), user.password());
            if (isTrue) {
                try {
                    this.userService.insertUser(user.toEntity());
                    return ResponseEntity.status(HttpStatus.CREATED).body("User/Created");
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }
            }  else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Inavalid/Camps");
            }
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationDTO auth) {
        try {
            String response = this.userService.findUserByNameAndPassword(auth.name(), auth.password());
            if (Objects.equals(response, "User Not Found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

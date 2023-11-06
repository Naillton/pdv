package com.api.pdv.controller;

import com.api.pdv.middleware.UserMiddleware;
import com.api.pdv.model.User;
import com.api.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final HttpHeaders responseHeaders = new HttpHeaders();

    @PostMapping(
            value = "/cadastrar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> insertUser(@RequestBody User user) {
        User u = this.userService.getUser(user.getName());
        if (u != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Already/Exist");
        } else {
            Boolean isTrue = UserMiddleware.ValidUser(user.getName(), user.getPassword().trim());
            if (isTrue) {
                try {
                    String md5 = UserMiddleware.getHashMd5(user.getPassword());
                    String encoded = UserMiddleware.getEncode64(md5);
                    user.setPassword(encoded);
                    this.userService.insertUser(user);
                    return ResponseEntity.status(HttpStatus.CREATED).body("User/Created");
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }
            }  else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Inavalid/Camps");
            }
        }
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String md5 = UserMiddleware.getHashMd5(user.getPassword());
        String encoded = UserMiddleware.getEncode64(md5);
        user.setPassword(encoded);
        User u = this.userService.loginService(user.getName(), user.getPassword());
        try {
            if (u != null) {
                UUID id = u.getId();
                responseHeaders.set(UserMiddleware.getIdInfo(), String.valueOf(id));
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.valueOf(id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping( value = "/list")
    public ResponseEntity<?> getUsers() {
        List<String> result = responseHeaders.get(UserMiddleware.getIdInfo());
        if (result == null || result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                User user = this.userService.findUserById(UUID.fromString(result.stream().findFirst().get()));
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                }
                List<User> users = this.userService.getUsers();
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping( value = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable UUID id) {
        List<String> result = responseHeaders.get(UserMiddleware.getIdInfo());
        if (result == null || result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                User u = this.userService.findUserById(id);
                User user = this.userService.findUserById(UUID.fromString(result.stream().findFirst().get()));
                if (u == null || user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                }
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @PutMapping( value = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody User user) {
        List<String> result = responseHeaders.get(UserMiddleware.getIdInfo());
        if (result == null || result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                User u = this.userService.findUserById(id);
                User us = this.userService.findUserById(UUID.fromString(result.stream().findFirst().get()));
                if (u == null || us == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                } else {
                    Boolean isTrue = UserMiddleware.ValidUser(user.getName(), user.getPassword().trim());
                    if (isTrue) {
                        String md5 = UserMiddleware.getHashMd5(user.getPassword());
                        String encoded = UserMiddleware.getEncode64(md5);
                        user.setPassword(encoded);
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
    }

    @DeleteMapping( value = "/delete/{id}" )
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {
        List<String> result = responseHeaders.get(UserMiddleware.getIdInfo());
        if (result == null || result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                User user = this.userService.findUserById(id);
                User u = this.userService.findUserById(UUID.fromString(result.stream().findFirst().get()));
                if (user == null || u == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                } else {
                    this.userService.deleteUser(id);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("User/Deleted");
                }
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @DeleteMapping( value = "/delete/all" )
    public ResponseEntity<String> deleteAllUsers() {
        List<String> result = responseHeaders.get(UserMiddleware.getIdInfo());
        if (result == null || result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                User user = this.userService.findUserById(UUID.fromString(result.stream().findFirst().get()));
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User/Not/Exist");
                }
                this.userService.deleteAllUsers();
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Users/Deleted");
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

}

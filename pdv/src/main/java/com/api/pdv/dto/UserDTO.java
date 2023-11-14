package com.api.pdv.dto;

import com.api.pdv.model.User;

public record UserDTO(String name, String password) {
    public User toEntity() {
        return new User(name, password);
    }
}

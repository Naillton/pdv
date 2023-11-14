package com.api.pdv.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record AuthenticationDTO(String name, String password) { }

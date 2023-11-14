package com.api.pdv.service;

import com.api.pdv.model.User;
import com.api.pdv.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${api.security.token.secret}")
    private String secret;

    public void insertUser(User user) {
        String hashedPass = encodePass(user.getPassword());
        user.setPassword(hashedPass);
        this.userRepository.save(user);
    }

    public List<User> getUsers() { return this.userRepository.findAll(); }

    public void deleteUser(UUID id) { this.userRepository.deleteById(id); }

    public void deleteAllUsers() { this.userRepository.deleteAll(); }

    public void updateUser(User user, UUID id) {
        User u = findUserById(id);
        u.setName(user.getName());
        u.setPassword(user.getPassword());
        this.userRepository.saveAndFlush(u);
    }

    public User findUserById(UUID id ) { return this.userRepository.findById(id)
            .stream().findFirst()
            .orElse(null);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public String findUserByNameAndPassword(String name, String password) {
        User user = this.userRepository.findByUsername(name);
        if (user == null) return "User Not Found";
        Boolean isTrue = this.decodePass(password, user.getPassword());
        if (isTrue) {
            return generateToken(user);
        }
        return "User Not Found";
    }

    private String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("PDV")
                .withSubject(user.getName())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(3)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("PDV")
                .build()
                .verify(token)
                .getSubject();
    }

    private String encodePass(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }

    private Boolean decodePass(String rawPass, String encodedPass) {
        return new BCryptPasswordEncoder().matches(rawPass, encodedPass);
    }
}

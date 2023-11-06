package com.api.pdv.service;

import com.api.pdv.model.User;
import com.api.pdv.repository.UserRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> getUsers() { return this.userRepository.findAll(); }

    public User getUser(String name) { return this.userRepository.findUserByName(name); }

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

    public User loginService(String name, String password) {
        return this.userRepository.findUserByPasswordAndName(name, password);
    }
}

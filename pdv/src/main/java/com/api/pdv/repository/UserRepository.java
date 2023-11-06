package com.api.pdv.repository;

import com.api.pdv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM users WHERE nome=?", nativeQuery = true)
    User findUserByName(String nome);

    @Query(value = "SELECT * FROM users WHERE nome=? AND password=?", nativeQuery = true)
    User findUserByPasswordAndName(String nome, String password);
}

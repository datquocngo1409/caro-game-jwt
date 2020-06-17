package com.example.carogamejwt.service;

import com.example.carogamejwt.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User findByUsername(String username);

    User findByToken(String token);
}

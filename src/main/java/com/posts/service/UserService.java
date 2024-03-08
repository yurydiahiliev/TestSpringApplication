package com.posts.service;

import com.posts.model.Users;

public interface UserService {
    Users findByUsername(String username);
    void save(Users users);
    boolean existsByUsername(String username);
}


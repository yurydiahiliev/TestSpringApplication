package com.posts.service;

import com.posts.model.UserDto;

import java.util.*;

public interface UserService {
    UserDto findByUsername(String username);
    UserDto findUserById(Long id);
    List<UserDto> findAllUsers();
    UserDto add(UserDto userDto);
    boolean existsByUsername(String username);
}


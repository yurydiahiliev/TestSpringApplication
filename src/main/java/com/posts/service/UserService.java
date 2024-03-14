package com.posts.service;

import com.posts.data.entities.UserEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public interface UserService {
    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> findByUserId(Long id);
    Mono<List<UserEntity>> findAllUsers();
    Mono<UserEntity> registerUser(UserEntity userDto);
    boolean existsByUsername(String username);
}


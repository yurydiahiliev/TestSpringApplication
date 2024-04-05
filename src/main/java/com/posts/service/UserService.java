package com.posts.service;

import com.posts.data.entities.PostEntity;
import com.posts.data.entities.UserEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface UserService {
    Mono<UserEntity> findByUsername(String username);

    Mono<UserEntity> findByUserId(Long id);
    Flux<UserEntity> findAllUsers();
    Mono<UserEntity> registerUser(UserEntity userDto);

    Mono<UserEntity> updateUser(Long id, UserEntity userEntity);
    Mono<Void> deleteUser(Long id);
    Mono<Boolean> existsByUsername(String username);
}


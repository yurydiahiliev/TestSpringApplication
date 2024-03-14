package com.posts.service.impl;

import com.posts.data.entities.UserEntity;
import com.posts.data.entities.UserRole;
import com.posts.data.repository.UserRepository;
import com.posts.exceptions.ApiException;
import com.posts.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user) {
         if (!userRepository.existsByUsername(user.getUsername())) {
             return createNewUser(user);
         } else {
             throw new ApiException("User is already exists!", "400");
         }
    }

    private Mono<UserEntity> createNewUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        return userRepository.save(user).doOnSuccess(u -> {
            log.info("IN registerUser - user: {} created", u);
        });
    }

    @Override
    public Mono<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Mono<UserEntity> findByUserId(Long id) {
        return userRepository.findById(id)
                             .switchIfEmpty(Mono.error(new RuntimeException("Can not find user by id: " + id)));
    }

    @Override
    public Mono<List<UserEntity>> findAllUsers() {
        return userRepository.findAll().collectList();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}

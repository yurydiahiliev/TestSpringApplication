package com.posts.service.impl;

import com.posts.data.entities.UserEntity;
import com.posts.data.entities.UserRole;
import com.posts.data.repository.UserRepository;
import com.posts.exceptions.ApiException;
import com.posts.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.existsByUsername(user.getUsername()).flatMap(userExists -> {
            if (!userExists) {
                return createNewUser(user);
            } else {
                return Mono.error(new ApiException("User is already exist!", HttpStatus.BAD_REQUEST));
            }
        });
    }

    private Mono<UserEntity> createNewUser(@NonNull UserEntity user) {
        if (isNull(user.getUsername()) || isNull(user.getPassword())) {
            return Mono.error(new ApiException("Username and password are required parameters!", HttpStatus.BAD_REQUEST));
        }

        if (user.getUsername().equalsIgnoreCase("admin")) {
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        return userRepository.save(user).doOnSuccess(u -> log.info("RegisterUser - user: {} created", u));
    }

    @Override
    public Mono<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public Mono<UserEntity> findByUserId(Long id) {
        return userRepository.findById(id)
                             .switchIfEmpty(Mono.error(new ApiException("Can not find user by id: " + id, HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<List<UserEntity>> findAllUsers() {
        return userRepository.findAll().collectList();
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
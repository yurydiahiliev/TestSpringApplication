package com.posts.service.impl;

import com.posts.data.entities.UserEntity;
import com.posts.data.entities.UserRole;
import com.posts.data.repository.UserRepository;
import com.posts.exceptions.ApiException;
import com.posts.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.existsByUsername(user.getUsername())
                .flatMap(userExists -> userExists ?
                        Mono.error(new ApiException("User is already exist!", HttpStatus.BAD_REQUEST)) :
                        createNewUser(user)
                );
    }

    private Mono<UserEntity> createNewUser(@NonNull UserEntity user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return Mono.error(new ApiException("Username and password are required parameters and can not be empty!", HttpStatus.BAD_REQUEST));
        }

        user.setRole(user.getUsername().equalsIgnoreCase("admin") ? UserRole.ADMIN : UserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());

        return userRepository.save(user)
                .doOnSuccess(u -> log.info("RegisterUser - user: {} created", u));
    }

    @Override
    public Mono<UserEntity> updateUser(Long id, UserEntity userEntity) {
        if (StringUtils.isEmpty(userEntity.getUsername()) || StringUtils.isEmpty(userEntity.getPassword())) {
            return Mono.error(new ApiException("Username and password are required parameters and can not be empty!", HttpStatus.BAD_REQUEST));
        }

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiException(format("Can not find user by id: %d", id), HttpStatus.NOT_FOUND)))
                .flatMap(existingUser -> {
                    existingUser.setUsername(userEntity.getUsername());
                    existingUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    existingUser.setFirst_name(userEntity.getFirst_name());
                    existingUser.setLast_name(userEntity.getLast_name());
                    existingUser.setEnabled(true);
                    existingUser.setRole(UserRole.USER);
                    return userRepository.save(existingUser)
                            .doOnSuccess(u -> log.info("User - user: {} updated", u));
                });
    }

    @Override
    public Mono<Void> deleteUser(Long userId) {
        return userRepository.deleteById(userId)
                .switchIfEmpty(Mono.error(new ApiException(format("Can not find user to delete by id: %d", userId), HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new ApiException(format("Can not find user by username: %s", username), HttpStatus.NOT_FOUND)));
    }


    @Override
    public Mono<UserEntity> findByUserId(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiException(format("Can not find user by id: %d", id), HttpStatus.NOT_FOUND)));
    }

    @Override
    public Flux<UserEntity> findAllUsers() {
        return userRepository.findAll()
                .switchIfEmpty(Mono.error(new ApiException("Can not find all users by id: %d", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
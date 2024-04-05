package com.posts.controller;

import com.posts.mapper.UserMapper;
import com.posts.model.UserDto;
import com.posts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Mono<List<UserDto>> getAllUsers() {
        return userService.findAllUsers()
                .map(userMapper::map)
                .collectList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> createUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userMapper.map(userDto))
                .map(userMapper::map);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userMapper.map(userDto))
                .map(userMapper::map);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
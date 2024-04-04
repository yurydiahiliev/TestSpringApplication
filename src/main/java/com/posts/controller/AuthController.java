package com.posts.controller;

import com.posts.data.entities.UserEntity;
import com.posts.mapper.UserMapper;
import com.posts.model.AuthRequestDto;
import com.posts.model.AuthResponseDto;
import com.posts.model.UserDto;
import com.posts.security.SecurityService;
import com.posts.security.UserPrincipal;
import com.posts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthController {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto) {
        UserEntity entity = userMapper.map(userDto);
        return userService.registerUser(entity).map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                              .flatMap(tokenDetails -> Mono.just(
                                  AuthResponseDto.builder()
                                                 .userId(tokenDetails.getUserId())
                                                 .token(tokenDetails.getToken())
                                                 .issuedAt(tokenDetails.getIssuedAt())
                                                 .expiresAt(tokenDetails.getExpiresAt())
                                                 .build()));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        UserPrincipal customPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userService.findByUserId(customPrincipal.getId()).map(userMapper::map);
    }
}
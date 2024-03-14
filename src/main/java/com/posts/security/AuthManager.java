package com.posts.security;

import com.posts.data.entities.UserEntity;
import com.posts.exceptions.UnauthorizedException;
import com.posts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    @Autowired
    private UserService userService;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return userService.findByUserId(principal.getId())
                          .filter(UserEntity::isEnabled)
                          .switchIfEmpty(Mono.error(new UnauthorizedException("User disabled")))
                          .map(user -> authentication);
    }
}
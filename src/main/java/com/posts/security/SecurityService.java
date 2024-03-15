package com.posts.security;

import com.posts.data.entities.UserEntity;
import com.posts.exceptions.AuthException;
import com.posts.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    private String issuer;


    private TokenDetails generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>() {{
            put("role", user.getRole());
            put("username", user.getUsername());
        }};
        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        long expirationTimeInMillis = expirationInSeconds * 1000L;
        Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);

        return generateToken(expirationDate, claims, subject);
    }

    private TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        Date createdDate = new Date();
        String token = Jwts.builder()
                           .setClaims(claims)
                           .setIssuer(issuer)
                           .setSubject(subject)
                           .setIssuedAt(createdDate)
                           .setId(UUID.randomUUID().toString())
                           .setExpiration(expirationDate)
                           .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                           .compact();

        return TokenDetails.builder()
                           .token(token)
                           .issuedAt(createdDate)
                           .expiresAt(expirationDate)
                           .build();
    }

    public Mono<TokenDetails> authenticate(String username, String password) {
        return userService.findByUsername(username)
                          .flatMap(user -> {
                              if (!user.isEnabled()) {
                                  return Mono.error(new AuthException("Account disabled!"));
                              }

                              if (!passwordEncoder.matches(password, user.getPassword())) {
                                  return Mono.error(new AuthException("Invalid password!"));
                              }

                              return Mono.just(generateToken(user).toBuilder()
                                                                  .userId(user.getId())
                                                                  .build());
                          })
                          .switchIfEmpty(
                              Mono.error(new AuthException("Invalid user data!")));
    }
}
package com.posts.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    private final String [] publicRoutes = {"/api/auth/register", "/api/auth/login"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationWebFilter authenticationWebFilter) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authenticationManager(authenticationManager())
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .authorizeExchange(auth -> {
                auth.pathMatchers(publicRoutes)
                    .permitAll();
                auth.pathMatchers(HttpMethod.OPTIONS)
                    .permitAll();
            })
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new AuthManager();
    }

    @Bean
    public AuthenticationWebFilter bearerAuthenticationFilter(ReactiveAuthenticationManager authenticationManager) {
        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return bearerAuthenticationFilter;
    }
}
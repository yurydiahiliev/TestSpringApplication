package com.posts.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtHandler jwtHandler;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Function<String, Mono<String>> getBearerValue = authValue -> {
        if (authValue == null || authValue.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Authorization token is empty"));
        }
        if (!authValue.startsWith(BEARER_PREFIX)) {
            return Mono.error(new IllegalArgumentException("Invalid authorization token format"));
        }
        return Mono.just(authValue.substring(BEARER_PREFIX.length()));
    };

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractHeader(exchange)
            .flatMap(getBearerValue)
            .flatMap(jwtHandler::checkAccessToken)
            .flatMap(UserAuthenticationBearer::create);
    }

    private Mono<String> extractHeader(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                                        .getHeaders()
                                        .getFirst(HttpHeaders.AUTHORIZATION));
    }
}
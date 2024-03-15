package com.posts.exceptions;

import org.springframework.http.HttpStatus;

public class AuthException extends ApiException {
    public AuthException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
package com.posts.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    protected String errorCode;
    protected String httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = String.valueOf(httpStatus.value());
        this.httpStatus = httpStatus.getReasonPhrase();
    }

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
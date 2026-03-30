package com.tss.springsecurity.exception;

import org.springframework.http.HttpStatus;

public class UserApiException extends ApplicationException {
    public UserApiException(String message) {
        super("USER_ALREADY_EXISTS", message,  HttpStatus.CONFLICT);
    }
}

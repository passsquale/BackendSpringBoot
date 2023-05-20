package ru.roslyackov.springboot.auth.exception;

import org.springframework.security.core.AuthenticationException;


public class UserOrEmailExistsException extends AuthenticationException {

    public UserOrEmailExistsException(String msg) {
        super(msg);
    }


    public UserOrEmailExistsException(String msg, Throwable t) {
        super(msg, t);
    }
}


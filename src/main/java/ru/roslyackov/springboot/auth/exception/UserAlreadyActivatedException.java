package ru.roslyackov.springboot.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyActivatedException extends AuthenticationException {

    public UserAlreadyActivatedException(String msg) {
        super(msg);
    }


    public UserAlreadyActivatedException(String msg, Throwable t) {
        super(msg, t);
    }
}

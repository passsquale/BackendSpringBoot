package ru.roslyackov.springboot.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtCommonException extends AuthenticationException {

    public JwtCommonException(String msg) {
        super(msg);
    }

    public JwtCommonException(String msg, Throwable t) {
        super(msg, t);
    }
}
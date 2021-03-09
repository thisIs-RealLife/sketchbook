package ru.oleg.sketchbook.security;

import org.springframework.http.HttpStatus;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    private HttpStatus status;

    public JwtAuthenticationException(String msg , HttpStatus status){
        super(msg);
    }
}

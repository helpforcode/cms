package com.example.cms.global;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseWrapper authenticationException(Exception e, WebRequest request) {
        e.printStackTrace();
        return ResponseWrapper.failed(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseWrapper exception(Exception e, WebRequest request) {
        e.printStackTrace();
        return ResponseWrapper.failed(e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseWrapper handleBindException(BindException e) {
        e.printStackTrace();
        if (e.getBindingResult().getAllErrors().size() > 0) {
            return ResponseWrapper.failed(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
        }
        return ResponseWrapper.failed(e.getMessage(), null);
    }
}

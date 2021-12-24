package com.example.mybatis.model;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSuchElementFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoSuchElementFoundException(String message) {
        super(message);
    }
}
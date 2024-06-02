package com.example.PageableTrain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundBookException extends RuntimeException{
    public NotFoundBookException(String message) {
        super(message);
    }
}

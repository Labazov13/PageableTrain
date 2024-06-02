package com.example.PageableTrain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EditBookException extends RuntimeException{
    public EditBookException(String message) {
        super(message);
    }
}

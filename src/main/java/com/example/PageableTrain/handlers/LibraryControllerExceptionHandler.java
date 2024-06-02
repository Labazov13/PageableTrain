package com.example.PageableTrain.handlers;

import com.example.PageableTrain.exceptions.AddingBookException;
import com.example.PageableTrain.exceptions.EditBookException;
import com.example.PageableTrain.exceptions.NotFoundBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class LibraryControllerExceptionHandler {
    @ExceptionHandler(NotFoundBookException.class)
    public ResponseEntity<?> handleNotFoundBookException(NotFoundBookException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(AddingBookException.class)
    public ResponseEntity<?> handleAddingBookException(AddingBookException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(EditBookException.class)
    public ResponseEntity<?> handleEditBookException(EditBookException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}

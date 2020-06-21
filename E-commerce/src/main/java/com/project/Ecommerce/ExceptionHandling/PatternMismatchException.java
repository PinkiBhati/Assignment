package com.project.Ecommerce.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.NOT_ACCEPTABLE)
public class PatternMismatchException extends RuntimeException {

    public PatternMismatchException(String message) {
        super(message);
    }
}

package com.project.Ecommerce.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class PasswordAndConfirmPasswordMismatchException extends RuntimeException
{
    public PasswordAndConfirmPasswordMismatchException(String message) {
        super(message);
    }
}
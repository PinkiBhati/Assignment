package com.project.Ecommerce.ExceptionHandling;

public class Success extends RuntimeException
{
    public Success(String message) {
        super(message);
    }
}
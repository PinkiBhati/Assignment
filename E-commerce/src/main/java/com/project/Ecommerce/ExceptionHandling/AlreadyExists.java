package com.project.Ecommerce.ExceptionHandling;

public class AlreadyExists extends RuntimeException
{
    public AlreadyExists(String message) {
        super(message);
    }
}
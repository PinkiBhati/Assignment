package com.project.Ecommerce.ExceptionHandling;

public class ProductVariationException extends RuntimeException
{
    public ProductVariationException(String message) {
        super(message);
    }
}
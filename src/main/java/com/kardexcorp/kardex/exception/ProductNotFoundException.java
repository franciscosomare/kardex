package com.kardexcorp.kardex.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String PRODUCT_NOT_FOUND = "Product not found on ::";

    public ProductNotFoundException(long id) {
        super(PRODUCT_NOT_FOUND + id);
    }
}
package com.litvaj.eshop.exception;

/**
 * Thrown when entity is not found in the DB
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}

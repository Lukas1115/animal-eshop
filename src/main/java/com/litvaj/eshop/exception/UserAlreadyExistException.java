package com.litvaj.eshop.exception;

/**
 * Thrown when try to register user already registered.
 */
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}

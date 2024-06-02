package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui un token JWT non è valido.
 */
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}

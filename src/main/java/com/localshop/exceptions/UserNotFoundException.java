package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui un utente non viene trovato.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

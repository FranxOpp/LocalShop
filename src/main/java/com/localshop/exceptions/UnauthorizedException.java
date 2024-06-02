package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui un utente non è autorizzato.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

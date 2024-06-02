package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui una richiesta non è valida.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

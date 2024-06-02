package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui si verifica un errore interno del server.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}

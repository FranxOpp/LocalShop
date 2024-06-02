package com.localshop.exceptions;

/**
 * Eccezione personalizzata per gestire i casi in cui una risorsa non viene trovata.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

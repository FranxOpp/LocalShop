package com.localshop.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore centralizzato delle eccezioni per l'applicazione.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gestisce le eccezioni di tipo UserNotFoundException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di tipo InvalidTokenException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidTokenException(InvalidTokenException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di tipo BadRequestException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di tipo UnauthorizedException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di tipo ResourceNotFoundException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di tipo InternalServerErrorException.
     *
     * @param ex l'eccezione da gestire
     * @return il messaggio di errore
     */
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalServerErrorException(InternalServerErrorException ex) {
        return ex.getMessage();
    }

    /**
     * Gestisce le eccezioni di validazione degli argomenti del metodo.
     *
     * @param ex      l'eccezione da gestire
     * @param request la richiesta web
     * @return la risposta dell'entità
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
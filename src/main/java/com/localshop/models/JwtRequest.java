package com.localshop.models;

import java.io.Serializable;

/**
 * Classe modello per rappresentare una richiesta di autenticazione JWT.
 */
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    /**
     * Costruttore predefinito necessario per la serializzazione.
     */
    public JwtRequest() {
    }

    /**
     * Costruttore con parametri per creare una richiesta JWT.
     *
     * @param username il nome utente
     * @param password la password
     */
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    /**
     * Restituisce il nome utente.
     *
     * @return il nome utente
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Imposta il nome utente.
     *
     * @param username il nome utente da impostare
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la password.
     *
     * @return la password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Imposta la password.
     *
     * @param password la password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

package com.localshop.models;

import java.io.Serializable;

/**
 * Classe modello per rappresentare una risposta di autenticazione JWT.
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    /**
     * Costruttore per creare una risposta JWT.
     *
     * @param jwttoken il token JWT
     */
    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    /**
     * Restituisce il token JWT.
     *
     * @return il token JWT
     */
    public String getToken() {
        return this.jwttoken;
    }
}

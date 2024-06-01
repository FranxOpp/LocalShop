package com.localshop.security;

import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Punto di ingresso dell'autenticazione JWT.
 * Gestisce i tentativi di accesso non autorizzati inviando un errore 401.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Invia un errore 401 (Unauthorized) in risposta ai tentativi di accesso non autorizzati.
     *
     * @param request il {@link jakarta.servlet.http.HttpServletRequest} che ha causato l'eccezione
     * @param response il {@link jakarta.servlet.http.HttpServletResponse} che verrà inviato al client
     * @param authException l'eccezione che ha causato l'errore di autenticazione
     * @throws IOException in caso di errore di input/output
     * @throws ServletException in caso di errore del servlet
     */
    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}

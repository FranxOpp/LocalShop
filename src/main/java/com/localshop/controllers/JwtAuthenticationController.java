package com.localshop.controllers;

import com.localshop.models.JwtRequest;
import com.localshop.models.JwtResponse;
import com.localshop.security.JwtTokenUtil;
import com.localshop.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le richieste di autenticazione JWT.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Crea un token di autenticazione JWT per un utente autenticato.
     *
     * @param authenticationRequest Oggetto che contiene le credenziali di autenticazione (username e password)
     * @return ResponseEntity contenente il token JWT
     * @throws Exception in caso di errori durante l'autenticazione
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        // Autentica l'utente con le credenziali fornite
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Carica i dettagli dell'utente
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // Genera il token JWT
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Restituisce la risposta contenente il token JWT
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Autentica l'utente con il nome utente e la password forniti.
     *
     * @param username Nome utente
     * @param password Password
     * @throws Exception in caso di utente disabilitato o credenziali non valide
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            // Esegue l'autenticazione
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            // Lancia un'eccezione se l'utente è disabilitato
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            // Lancia un'eccezione se le credenziali sono non valide
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

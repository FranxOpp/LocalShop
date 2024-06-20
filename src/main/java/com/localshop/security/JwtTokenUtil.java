package com.localshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utilità per la gestione dei token JWT.
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret = "secret";

    /**
     * Estrae il nome utente dal token JWT.
     *
     * @param token il token JWT
     * @return il nome utente estratto dal token
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Estrae la data di scadenza dal token JWT.
     *
     * @param token il token JWT
     * @return la data di scadenza estratta dal token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Estrae un claim specifico dal token JWT.
     *
     * @param token il token JWT
     * @param claimsResolver la funzione per risolvere il claim
     * @param <T> il tipo del claim
     * @return il claim estratto
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Estrae tutti i claims dal token JWT.
     *
     * @param token il token JWT
     * @return tutti i claims estratti dal token
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Controlla se il token JWT è scaduto.
     *
     * @param token il token JWT
     * @return true se il token è scaduto, false altrimenti
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Genera un token JWT per l'utente specificato.
     *
     * @param userDetails i dettagli dell'utente
     * @return il token JWT generato
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Esegue la generazione del token JWT.
     *
     * @param claims i claims da includere nel token
     * @param subject il soggetto del token
     * @return il token JWT generato
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Valida il token JWT confrontandolo con i dettagli dell'utente.
     *
     * @param token il token JWT
     * @param userDetails i dettagli dell'utente
     * @return true se il token è valido, false altrimenti
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

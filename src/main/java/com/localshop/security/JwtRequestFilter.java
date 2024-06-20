package com.localshop.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

/**
 * Filtro per richieste JWT che viene eseguito una volta per ogni richiesta.
 * Controlla la presenza di un token JWT nell'intestazione della richiesta e, se presente e valido,
 * imposta l'autenticazione nel contesto di sicurezza di Spring.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Filtra le richieste HTTP per l'autenticazione JWT
     *
     * @param request il {@link HttpServletRequest} che viene filtrato
     * @param response il {@link HttpServletResponse} che viene filtrato
     * @param chain la {@link FilterChain} per filtrare ulteriori richieste
     * @throws ServletException in caso di errori nel filtro
     * @throws IOException in caso di errori di input/output
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // Estrae il token JWT dall'intestazione della richiesta
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Valida il token e imposta l'autenticazione nel contesto di sicurezza di Spring
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                // Estrae i ruoli dal token JWT
                List<GrantedAuthority> authorities = ((List<?>) jwtTokenUtil.getClaimFromToken(jwtToken, claims -> claims.get("role")))
                        .stream()
                        .map(role -> new SimpleGrantedAuthority((String) role))
                        .collect(Collectors.toList());

                // Imposta l'autenticazione con i ruoli estratti
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

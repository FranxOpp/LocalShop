package com.localshop.config;

import com.localshop.security.JwtAuthenticationEntryPoint;
import com.localshop.security.JwtRequestFilter;
import com.localshop.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configura la sicurezza per l'applicazione Spring Boot usando Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configura la catena dei filtri di sicurezza per l'applicazione.
     *
     * @param http il contesto di sicurezza HTTP
     * @return la catena dei filtri di sicurezza configurata
     * @throws Exception in caso di errori durante la configurazione
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disabilita la protezione CSRF (non necessaria per le API stateless)
                .csrf(csrf -> csrf.disable())
                // Configura le autorizzazioni per le richieste HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permette l'accesso a tutte le richieste che iniziano con "/api/"
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/commerciante/**").hasRole("COMMERCIANTE")
                        .requestMatchers("/api/cliente/**").hasRole("CLIENTE")
                        // Richiede l'autenticazione per tutte le altre richieste
                        .anyRequest().authenticated()
                )
                // Configura la gestione delle eccezioni di autenticazione
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                // Configura la gestione delle sessioni per essere stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Aggiunge il filtro JWT prima del filtro di autenticazione standard
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configura il codificatore delle password usando BCrypt.
     *
     * @return l'istanza del codificatore delle password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura il gestore delle autenticazioni con il servizio di dettagli utente e il codificatore delle password.
     *
     * @param http il contesto di sicurezza HTTP
     * @return l'istanza del gestore delle autenticazioni
     * @throws Exception in caso di errori durante la configurazione
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configura il gestore delle autenticazioni con il servizio di dettagli utente e il codificatore delle password
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }
}

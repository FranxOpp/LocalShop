package com.localshop.repositories;

import com.localshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    /**
     * Trova un utente per nome utente.
     *
     * @param username il nome utente
     * @return un Optional contenente l'utente se trovato, altrimenti vuoto
     */
    Optional<User> findByUsername(String username);

    /**
     * Verifica se un nome utente esiste.
     *
     * @param username il nome utente
     * @return true se il nome utente esiste, false altrimenti
     */
    boolean existsByUsername(String username);

    /**
     * Trova un utente per email.
     *
     * @param email l'email dell'utente
     * @return un Optional contenente l'utente se trovato, altrimenti vuoto
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se un'email esiste.
     *
     * @param email l'email dell'utente
     * @return true se l'email esiste, false altrimenti
     */
    boolean existsByEmail(String email);

    /**
     * Trova utenti per ruolo.
     *
     * @param role il ruolo dell'utente
     * @return una lista di utenti con il ruolo specificato
     */
    List<User> findByRole(String role);

    /**
     * Elimina un utente per nome utente.
     *
     * @param username il nome utente
     */
    void deleteByUsername(String username);

    /**
     * Trova un utente per token di reset della password.
     *
     * @param resetToken il token di reset della password
     * @return un Optional contenente l'utente se trovato, altrimenti vuoto
     */
    Optional<User> findByResetToken(String resetToken);

}
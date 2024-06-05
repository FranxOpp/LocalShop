package com.localshop.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Classe modello per rappresentare un utente.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false,unique = true)
    private String email;

    private String resetToken;
    private Date resetTokenExpiryDate;

    /**
     * Restituisce l'ID dell'utente.
     *
     * @return l'ID dell'utente
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param id l'ID da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome utente.
     *
     * @return il nome utente
     */
    public String getUsername() {
        return username;
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
     * Restituisce l'email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email
     * @param email email da impostare
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Restituisce la password.
     *
     * @return la password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password.
     *
     * @param password la password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il ruolo dell'utente.
     *
     * @return il ruolo dell'utente
     */
    public String getRole() {
        return role;
    }

    /**
     * Imposta il ruolo dell'utente.
     *
     * @param role il ruolo da impostare
     */
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * Imposta il token di reset della password.
     *
     * @param resetToken il token di reset della password da impostare
     */
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    /**
     * Restituisce la data di scadenza del token di reset della password.
     *
     * @return la data di scadenza del token di reset della password
     */
    public Date getResetTokenExpiryDate() {
        return resetTokenExpiryDate;
    }

    /**
     * Imposta la data di scadenza del token di reset della password.
     *
     * @param resetTokenExpiryDate la data di scadenza del token di reset della password da impostare
     */
    public void setResetTokenExpiryDate(Date resetTokenExpiryDate) {
        this.resetTokenExpiryDate = resetTokenExpiryDate;
    }


}

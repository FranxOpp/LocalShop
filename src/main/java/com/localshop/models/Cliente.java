package com.localshop.models;

import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente extends User{

    @Id
    @Column(name = "codice_fiscale", nullable = false, unique = true)
    private String codiceFiscale;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters...

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
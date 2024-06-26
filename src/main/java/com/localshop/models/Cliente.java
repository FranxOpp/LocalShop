package com.localshop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
public class Cliente extends User{

    @Id
    @Column(name = "codice_fiscale", nullable = false, unique = true)
    private String codiceFiscale;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

}
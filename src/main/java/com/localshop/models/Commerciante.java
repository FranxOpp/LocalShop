package com.localshop.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Commerciante")
public class Commerciante extends User{

    @Id
    @Column(name = "partita_iva", nullable = false, unique = true)
    private String partitaIva;


    @OneToMany(mappedBy = "commerciante", cascade = CascadeType.ALL)
    private List<Negozio> negozi;

    // Getters and Setters

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }



    public List<Negozio> getNegozi() {
        return negozi;
    }

    public void setNegozi(List<Negozio> negozi) {
        this.negozi = negozi;
    }
}

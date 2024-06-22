package com.localshop.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "commerciante")
public class Commerciante extends User{

    @Id
    @Column(name = "partita_iva", nullable = false, unique = true)
    private String partitaIva;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "commerciante", cascade = CascadeType.ALL)
    private List<Negozio> negozi;

    // Getters and Setters...

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Negozio> getNegozi() {
        return negozi;
    }

    public void setNegozi(List<Negozio> negozi) {
        this.negozi = negozi;
    }
}

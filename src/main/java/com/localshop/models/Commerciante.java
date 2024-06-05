package com.localshop.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="commerciante")
public class Commerciante extends User{

    @OneToMany(mappedBy = "commerciante",cascade = CascadeType.ALL)
    private List<Negozio> negozi;

    //Getters e Setters

    public List<Negozio> getNegozi() {
        return negozi;
    }

    public void setNegozi(List<Negozio> negozi) {
        this.negozi = negozi;
    }
}

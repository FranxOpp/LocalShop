package com.localshop.models;

import javax.persistence.*;
import java.util.List;

/**
 * Classe modello per rappresentare un negozio.
 */
@Entity
@Table(name = "shops")
public class Negozio {

    @Id
    @Column(name = "piva", nullable = false, unique = true)
    private String piva;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String indirizzo;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "negozio", cascade = CascadeType.ALL)
    private List<Product> prodotti;

    @ManyToOne
    @JoinColumn(name = "commerciante_id", nullable = false)
    private Commerciante commerciante;

    // Getters e Setters

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Product> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<Product> prodotti) {
        this.prodotti = prodotti;
    }

    public Commerciante getCommerciante() {
        return commerciante;
    }

    public void setCommerciante(Commerciante commerciante) {
        this.commerciante = commerciante;
    }
}

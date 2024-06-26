package com.localshop.models;

import jakarta.persistence.*;

/**
 * Classe modello per rappresentare un prodotto.
 */
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "negozio_id",nullable = false)
    private Negozio negozio;

    /**
     * Restituisce l'ID del prodotto.
     *
     * @return l'ID del prodotto
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'ID del prodotto.
     *
     * @param id l'ID da impostare
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome del prodotto.
     *
     * @return il nome del prodotto
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del prodotto.
     *
     * @param name il nome da impostare
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce la descrizione del prodotto.
     *
     * @return la descrizione del prodotto
     */
    public String getDescription() {
        return description;
    }

    /**
     * Imposta la descrizione del prodotto.
     *
     * @param description la descrizione da impostare
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Restituisce il prezzo del prodotto.
     *
     * @return il prezzo del prodotto
     */
    public double getPrice() {
        return price;
    }

    /**
     * Imposta il prezzo del prodotto.
     *
     * @param price il prezzo da impostare
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Restituisce il negozio associato al prodotto.
     *
     * @return il negozio associato al prodotto
     */
    public Negozio getNegozio() {
        return negozio;
    }

    /**
     * Imposta il negozio associato al prodotto.
     *
     * @param negozio il negozio da impostare
     */
    public void setNegozio(Negozio negozio) {
        this.negozio = negozio;
    }



}

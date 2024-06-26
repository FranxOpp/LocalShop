package com.localshop.models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Classe modello per rappresentare un carrello.
 */
@Entity
@Table(name = "Carrello")
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL)
    private List<ElementoCarrello> items;

    @OneToOne(mappedBy = "carrello")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable = false)
    private Cliente cliente;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ElementoCarrello> getItems() {
        return items;
    }

    public void setItems(List<ElementoCarrello> items) {
        this.items = items;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}

package com.localshop.models;

import javax.persistence.*;
import java.util.List;

/**
 * Classe modello per rappresentare un ordine.
 */
@Entity
@Table(name = "orders")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float totale;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<OrdineDettaglio> dettagli;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrello_id", referencedColumnName = "id")
    private Carrello carrello;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String indirizzoSpedizione;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<OrdineDettaglio> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<OrdineDettaglio> dettagli) {
        this.dettagli = dettagli;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

}

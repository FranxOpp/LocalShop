package com.localshop.controllers;

import com.localshop.models.Carrello;
import com.localshop.services.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le operazioni sul carrello.
 */
@RestController
@RequestMapping("/api/carts")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    /**
     * Aggiunge un prodotto al carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @param quantity la quantità del prodotto
     * @return il carrello aggiornato
     */
    @PostMapping("/{carrelloId}/add")
    public ResponseEntity<Carrello> addProductToCart(@PathVariable Long carrelloId, @RequestParam Long productId, @RequestParam int quantity) {
        Carrello carrello = carrelloService.addProductToCart(carrelloId, productId, quantity);
        return ResponseEntity.ok(carrello);
    }

    /**
     * Rimuove un prodotto dal carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @return il carrello aggiornato
     */
    @PostMapping("/{carrelloId}/remove")
    public ResponseEntity<Carrello> removeProductFromCart(@PathVariable Long carrelloId, @RequestParam Long productId) {
        Carrello carrello = carrelloService.removeProductFromCart(carrelloId, productId);
        return ResponseEntity.ok(carrello);
    }

    /**
     * Aggiorna la quantità di un prodotto nel carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @param quantity la nuova quantità del prodotto
     * @return il carrello aggiornato
     */
    @PostMapping("/{carrelloId}/update")
    public ResponseEntity<Carrello> updateProductQuantity(@PathVariable Long carrelloId, @RequestParam Long productId, @RequestParam int quantity) {
        Carrello carrello = carrelloService.updateProductQuantity(carrelloId, productId, quantity);
        return ResponseEntity.ok(carrello);
    }

    /**
     * Calcola il totale momentaneo del carrello.
     *
     * @param carrelloId l'ID del carrello
     * @return il totale del carrello
     */
    @GetMapping("/{carrelloId}/total")
    public ResponseEntity<Float> calculateTotal(@PathVariable Long carrelloId) {
        float total = carrelloService.calculateTotal(carrelloId);
        return ResponseEntity.ok(total);
    }

    /**
     * Recupera il carrello tramite il suo ID.
     *
     * @param carrelloId l'ID del carrello
     * @return il carrello trovato
     */
    @GetMapping("/{carrelloId}")
    public ResponseEntity<Carrello> getCarrello(@PathVariable Long carrelloId) {
        Carrello carrello = carrelloService.getCarrello(carrelloId);
        return ResponseEntity.ok(carrello);
    }
}

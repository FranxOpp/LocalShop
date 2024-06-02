package com.localshop.controllers;

import com.localshop.models.CartItem;
import com.localshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller per gestire le operazioni sul carrello.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    /**
     * Restituisce la lista di tutti gli elementi nel carrello.
     *
     * @return lista di tutti gli elementi nel carrello
     */
    @GetMapping
    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }

    /**
     * Aggiunge un elemento al carrello.
     *
     * @param cartItem l'elemento da aggiungere
     * @return l'elemento aggiunto
     */
    @PostMapping
    public CartItem addItemToCart(@RequestBody CartItem cartItem) {
        return cartRepository.save(cartItem);
    }

    /**
     * Rimuove un elemento dal carrello.
     *
     * @param id l'ID dell'elemento da rimuovere
     * @return ResponseEntity vuoto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long id) {
        Optional<CartItem> optionalCartItem = cartRepository.findById(id);
        if (optionalCartItem.isPresent()) {
            cartRepository.delete(optionalCartItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

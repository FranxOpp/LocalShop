package com.localshop.controllers;

import com.localshop.models.Product;
import com.localshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller per gestire le operazioni sui prodotti.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Restituisce la lista di tutti i prodotti.
     *
     * @return lista di tutti i prodotti
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Crea un nuovo prodotto.
     *
     * @param product il prodotto da creare
     * @return il prodotto creato
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    /**
     * Cerca prodotti in base al nome.
     *
     * @param name il nome da cercare
     * @return lista di prodotti che contengono il nome specificato
     */
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productRepository.findByNameContaining(name);
    }

    /**
     * Filtra prodotti in base al prezzo.
     *
     * @param minPrice il prezzo minimo
     * @param maxPrice il prezzo massimo
     * @return lista di prodotti che rientrano nell'intervallo di prezzo specificato
     */
    @GetMapping("/filter")
    public List<Product> filterProducts(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Aggiorna un prodotto esistente.
     *
     * @param id l'ID del prodotto da aggiornare
     * @param productDetails i dettagli aggiornati del prodotto
     * @return il prodotto aggiornato
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            return ResponseEntity.ok(productRepository.save(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un prodotto esistente.
     *
     * @param id l'ID del prodotto da eliminare
     * @return ResponseEntity vuoto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

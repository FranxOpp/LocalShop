package com.localshop.controllers;

import com.localshop.models.Product;
import com.localshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public List <Product> searchProducts(@RequestParam String name){
        return productRepository.findByNameContaining(name);
    }

    @GetMapping("/filter")
    public List <Product> filterProducts (@RequestParam Double minPrice,@RequestParam Double maxPrice){
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

 }


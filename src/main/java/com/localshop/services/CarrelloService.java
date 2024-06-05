package com.localshop.services;

import com.localshop.models.Carrello;
import com.localshop.models.ElementoCarrello;
import com.localshop.models.Product;
import com.localshop.repositories.CarrelloRepository;
import com.localshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servizio per la gestione delle operazioni legate al carrello.
 */
@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Aggiunge un prodotto al carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @param quantity la quantità del prodotto
     * @return il carrello aggiornato
     */
    public Carrello addProductToCart(Long carrelloId, Long productId, int quantity) {
        Optional<Carrello> carrelloOptional = carrelloRepository.findById(carrelloId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (carrelloOptional.isPresent() && productOptional.isPresent()) {
            Carrello carrello = carrelloOptional.get();
            Product product = productOptional.get();
            ElementoCarrello elementoCarrello = new ElementoCarrello();
            elementoCarrello.setCarrello(carrello);
            elementoCarrello.setProduct(product);
            elementoCarrello.setQuantity(quantity);
            carrello.getItems().add(elementoCarrello);
            return carrelloRepository.save(carrello);
        } else {
            throw new RuntimeException("Carrello o prodotto non trovato");
        }
    }

    /**
     * Rimuove un prodotto dal carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @return il carrello aggiornato
     */
    public Carrello removeProductFromCart(Long carrelloId, Long productId) {
        Optional<Carrello> carrelloOptional = carrelloRepository.findById(carrelloId);

        if (carrelloOptional.isPresent()) {
            Carrello carrello = carrelloOptional.get();
            carrello.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
            return carrelloRepository.save(carrello);
        } else {
            throw new RuntimeException("Carrello non trovato");
        }
    }

    /**
     * Aggiorna la quantità di un prodotto nel carrello.
     *
     * @param carrelloId l'ID del carrello
     * @param productId l'ID del prodotto
     * @param quantity la nuova quantità del prodotto
     * @return il carrello aggiornato
     */
    public Carrello updateProductQuantity(Long carrelloId, Long productId, int quantity) {
        Optional<Carrello> carrelloOptional = carrelloRepository.findById(carrelloId);

        if (carrelloOptional.isPresent()) {
            Carrello carrello = carrelloOptional.get();
            for (ElementoCarrello item : carrello.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
            return carrelloRepository.save(carrello);
        } else {
            throw new RuntimeException("Carrello non trovato");
        }
    }

    /**
     * Calcola il totale momentaneo del carrello.
     *
     * @param carrelloId l'ID del carrello
     * @return il totale del carrello
     */
    public float calculateTotal(Long carrelloId) {
        Optional<Carrello> carrelloOptional = carrelloRepository.findById(carrelloId);

        if (carrelloOptional.isPresent()) {
            Carrello carrello = carrelloOptional.get();
            float total = 0;
            for (ElementoCarrello item : carrello.getItems()) {
                total += item.getProduct().getPrice() * item.getQuantity();
            }
            return total;
        } else {
            throw new RuntimeException("Carrello non trovato");
        }
    }

    /**
     * Recupera il carrello tramite il suo ID.
     *
     * @param carrelloId l'ID del carrello
     * @return il carrello trovato
     */
    public Carrello getCarrello(Long carrelloId) {
        return carrelloRepository.findById(carrelloId).orElseThrow(() -> new RuntimeException("Carrello non trovato"));
    }
}

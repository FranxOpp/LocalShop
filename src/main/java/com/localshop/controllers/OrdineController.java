package com.localshop.controllers;

import com.localshop.models.Ordine;
import com.localshop.models.OrdineDettaglio;
import com.localshop.repositories.OrdineRepository;
import com.localshop.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller per gestire le operazioni sugli ordini.
 */
@RestController
@RequestMapping("/api/orders")
public class OrdineController {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Restituisce la lista di tutti gli ordini.
     *
     * @return lista di tutti gli ordini
     */
    @GetMapping
    public List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }

    /**
     * Crea un nuovo ordine.
     *
     * @param ordine l'ordine da creare
     * @return l'ordine creato
     */
    @PostMapping
    public Ordine createOrdine(@RequestBody Ordine ordine) {
        ordine.setStatus("PROCESSING");
        Ordine savedOrdine = ordineRepository.save(ordine);
        sendOrderConfirmationEmail(savedOrdine);
        return savedOrdine;
    }

    /**
     * Aggiorna lo stato di un ordine.
     *
     * @param id l'ID dell'ordine da aggiornare
     * @param status il nuovo stato dell'ordine
     * @return l'ordine aggiornato
     */
    @PutMapping("/{id}")
    public ResponseEntity<Ordine> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<Ordine> optionalOrdine = ordineRepository.findById(id);
        if (optionalOrdine.isPresent()) {
            Ordine ordine = optionalOrdine.get();
            ordine.setStatus(status);
            return ResponseEntity.ok(ordineRepository.save(ordine));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un ordine.
     *
     * @param id l'ID dell'ordine da eliminare
     * @return ResponseEntity vuoto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        Optional<Ordine> optionalOrdine = ordineRepository.findById(id);
        if (optionalOrdine.isPresent()) {
            ordineRepository.delete(optionalOrdine.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Invia l'ordine e manda un'email di conferma.
     *
     * @param ordine l'ordine da inviare
     * @return l'ordine inviato
     */
    @PostMapping("/send")
    public ResponseEntity<Ordine> sendOrder(@RequestBody Ordine ordine) {
        ordine.setStatus("PROCESSING");
        Ordine savedOrdine = ordineRepository.save(ordine);
        sendOrderConfirmationEmail(savedOrdine);
        return ResponseEntity.ok(savedOrdine);
    }

    private void sendOrderConfirmationEmail(Ordine ordine) {
        String to = ordine.getCliente().getEmail();
        String subject = "Order Confirmation";
        String text = "Hi, " + ordine.getCliente().getUsername() + ",\nYour order has been placed successfully.\nOrder ID: " + ordine.getId();
        emailService.sendEmail(to, subject, text);
    }
}


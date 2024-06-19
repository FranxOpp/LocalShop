package com.localshop.controllers;

import com.localshop.models.Ordine;
import com.localshop.repositories.OrdineRepository;
import com.localshop.services.EmailService;
import org.aspectj.weaver.ast.Or;
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


    /**
     * Recupera un ordine per ID.
     * Permetterà al front-end di recuperare tutte le informazioni di un ordine specifico
     * @param id l'ID dell'ordine da recuperare
     * @return l'ordine recuperato
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ordine> getOrdineById (@PathVariable Long id) {
        Optional<Ordine>ordine = ordineRepository.findById(id);
        return ordine.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    private void sendOrderConfirmationEmail(Ordine ordine) {
        String to = ordine.getCliente().getEmail();
        String subject = "Order Confirmation";
        String text = "Hi, " + ordine.getCliente().getUsername() + ",\nYour order has been placed successfully.\nOrder ID: " + ordine.getId();
        emailService.sendEmail(to, subject, text);
    }
}

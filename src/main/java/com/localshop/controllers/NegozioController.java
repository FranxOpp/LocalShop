package com.localshop.controllers;

import com.localshop.models.Negozio;
import com.localshop.models.Commerciante;
import com.localshop.repositories.NegozioRepository;
import com.localshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller per gestire le operazioni sui negozi.
 */
@RestController
@RequestMapping("/api/shops")
public class NegozioController {

    @Autowired
    private NegozioRepository negozioRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Restituisce la lista di tutti i negozi.
     *
     * @return lista di tutti i negozi
     */
    @GetMapping
    public List<Negozio> getAllNegozi() {
        return negozioRepository.findAll();
    }

    /**
     * Crea un nuovo negozio.
     *
     * @param negozio il negozio da creare
     * @param commercianteId l'ID del commerciante che possiede il negozio
     * @return il negozio creato
     */
    @PostMapping
    public ResponseEntity<?> createNegozio(@RequestBody Negozio negozio, @RequestParam Long commercianteId) {
        Optional<Commerciante> optionalCommerciante = userRepository.findById(commercianteId).map(user -> (Commerciante) user);
        if (optionalCommerciante.isPresent()) {
            Commerciante commerciante = optionalCommerciante.get();
            negozio.setCommerciante(commerciante);
            Negozio savedNegozio = negozioRepository.save(negozio);
            return ResponseEntity.ok(savedNegozio);
        } else {
            return ResponseEntity.badRequest().body("Invalid commerciante ID.");
        }
    }

    /**
     * Aggiorna un negozio esistente.
     *
     * @param piva la P.IVA del negozio da aggiornare
     * @param negozioDetails i dettagli aggiornati del negozio
     * @return il negozio aggiornato
     */
    @PutMapping("/{piva}")
    public ResponseEntity<Negozio> updateNegozio(@PathVariable String piva, @RequestBody Negozio negozioDetails) {
        Optional<Negozio> optionalNegozio = negozioRepository.findById(piva);
        if (optionalNegozio.isPresent()) {
            Negozio negozio = optionalNegozio.get();
            negozio.setNome(negozioDetails.getNome());
            negozio.setIndirizzo(negozioDetails.getIndirizzo());
            negozio.setEmail(negozioDetails.getEmail());
            negozio.setTelefono(negozioDetails.getTelefono());
            negozio.setProdotti(negozioDetails.getProdotti());
            return ResponseEntity.ok(negozioRepository.save(negozio));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un negozio.
     *
     * @param piva la P.IVA del negozio da eliminare
     * @return ResponseEntity vuoto
     */
    @DeleteMapping("/{piva}")
    public ResponseEntity<Void> deleteNegozio(@PathVariable String piva) {
        Optional<Negozio> optionalNegozio = negozioRepository.findById(piva);
        if (optionalNegozio.isPresent()) {
            negozioRepository.delete(optionalNegozio.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

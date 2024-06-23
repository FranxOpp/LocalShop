package com.localshop.controllers;

import com.localshop.models.Recensione;
import com.localshop.models.Negozio;
import com.localshop.models.Cliente;
import com.localshop.repositories.RecensioneRepository;
import com.localshop.repositories.NegozioRepository;
import com.localshop.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recensioni")
@Validated
public class RecensioneController {

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private NegozioRepository negozioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Aggiunge una nuova recensione.
     *
     * @param recensione la recensione da aggiungere
     * @param negozioId l'ID del negozio
     * @param clienteId l'ID del cliente
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<?> addRecensione(@Valid @RequestBody Recensione recensione, @RequestParam Long negozioId, @RequestParam Long clienteId) {
        Optional<Negozio> negozioOpt = negozioRepository.findById(negozioId);
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);

        if (negozioOpt.isPresent() && clienteOpt.isPresent()) {
            recensione.setNegozio(negozioOpt.get());
            recensione.setCliente(clienteOpt.get());
            recensione.setData(new Date());
            recensioneRepository.save(recensione);
            return ResponseEntity.ok("Recensione aggiunta con successo");
        } else {
            return ResponseEntity.badRequest().body("Negozio o Cliente non trovato");
        }
    }

    /**
     * Restituisce tutte le recensioni di un negozio.
     *
     * @param negozioId l'ID del negozio
     * @return lista di recensioni del negozio
     */
    @GetMapping("/negozio/{negozioId}")
    public ResponseEntity<List<Recensione>> getRecensioniPerNegozio(@PathVariable Long negozioId) {
        Optional<Negozio> negozioOpt = negozioRepository.findById(negozioId);
        if (negozioOpt.isPresent()) {
            List<Recensione> recensioni = recensioneRepository.findByNegozio(negozioOpt.get());
            return ResponseEntity.ok(recensioni);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

package com.localshop.services;

import com.localshop.models.Ordine;
import com.localshop.repositories.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrdineStatusUpdateService {

    @Autowired
    private OrdineRepository ordineRepository;

    // Array di stati che rappresentano le fasi di avanzamento dell'ordine.
    private final String[] statuses = {"PROCESSING", "SHIPPED", "OUT FOR DELIVERY", "DELIVERED"};

    /**
     *  Metodo per aggiornare lo stato degli ordini ogni 15 secondi.
     */

    @Scheduled(fixedRate = 15000)
    public void updateOrderStatus() {
        // Recupera tutti gli ordini dal repository.
        List<Ordine> ordini = ordineRepository.findAll();
        Random random = new Random();

        // Itera su ogni ordine nella lista.
        for (Ordine ordine : ordini) {
            // Ottiene l'indice dello stato attuale dell'ordine.
            int currentStatusIndex = getCurrentStatusIndex(ordine.getStatus());
            // Se lo stato attuale non è l'ultimo nella lista degli stati, aggiorna lo stato.
            if (currentStatusIndex < statuses.length - 1) {
                // Imposta il nuovo stato dell'ordine al prossimo stato nella lista.
                ordine.setStatus(statuses[currentStatusIndex + 1]);
                // Salva l'ordine aggiornato nel repository.
                ordineRepository.save(ordine);
            }
        }
    }

    // Metodo per ottenere l'indice dello stato attuale nella lista degli stati.
    private int getCurrentStatusIndex(String status) {
        for (int i = 0; i < statuses.length; i++) {
            // Confronta lo stato corrente con quelli nella lista degli stati.
            if (statuses[i].equals(status)) {
                return i;
            }
        }
        // Restituisce -1 se lo stato non è trovato nella lista.
        return -1;
    }
}

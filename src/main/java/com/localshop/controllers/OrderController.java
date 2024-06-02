package com.localshop.controllers;

import com.localshop.models.Order;
import com.localshop.repositories.OrderRepository;
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
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Restituisce la lista di tutti gli ordini.
     *
     * @return lista di tutti gli ordini
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Crea un nuovo ordine.
     *
     * @param order l'ordine da creare
     * @return l'ordine creato
     */
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    /**
     * Aggiorna lo stato di un ordine.
     *
     * @param id l'ID dell'ordine da aggiornare
     * @param status il nuovo stato dell'ordine
     * @return l'ordine aggiornato
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(order));
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
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            orderRepository.delete(optionalOrder.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

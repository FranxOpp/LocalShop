package com.localshop.controllers;

import com.localshop.models.User;
import com.localshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per gestire le operazioni sugli utenti.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Restituisce la lista di tutti gli utenti.
     *
     * @return lista di tutti gli utenti
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Crea un nuovo utente.
     *
     * @param user l'utente da creare
     * @return l'utente creato
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Registra un nuovo utente.
     *
     * @param user l'utente da registrare
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        // Verifica se il nome utente esiste già
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Codifica la password dell'utente
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Imposta il ruolo predefinito per l'utente
        user.setRole("ROLE_USER");
        // Salva l'utente nel repository
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}

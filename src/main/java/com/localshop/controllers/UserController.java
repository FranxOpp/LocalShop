package com.localshop.controllers;

import com.localshop.models.Cliente;
import com.localshop.models.Commerciante;
import com.localshop.models.User;
import com.localshop.repositories.UserRepository;
import com.localshop.repositories.ClienteRepository;
import com.localshop.repositories.CommercianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller per gestire le operazioni sugli utenti.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CommercianteRepository commercianteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Restituisce la lista di tutti gli utenti.
     *
     * @return lista di tutti gli utenti
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Solo l'admin può visualizzare tutti gli utenti
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Registra un nuovo cliente.
     *
     * @param cliente il cliente da registrare
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping("/register/cliente")
    public ResponseEntity<?> registerCliente(@RequestBody Cliente cliente) {
        if (userRepository.existsByUsername(cliente.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        cliente.setRole("ROLE_CLIENTE");
        clienteRepository.save(cliente);

        return ResponseEntity.ok("Cliente registered successfully!");
    }

    /**
     * Registra un nuovo commerciante.
     *
     * @param commerciante il commerciante da registrare
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping("/register/commerciante")
    public ResponseEntity<?> registerCommerciante(@RequestBody Commerciante commerciante) {
        if (userRepository.existsByUsername(commerciante.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        commerciante.setPassword(passwordEncoder.encode(commerciante.getPassword()));
        commerciante.setRole("ROLE_COMMERCIANTE");
        commercianteRepository.save(commerciante);

        return ResponseEntity.ok("Commerciante registered successfully!");
    }

    /**
     * Aggiorna un utente esistente.
     *
     * @param id l'ID dell'utente da aggiornare
     * @param userDetails i dettagli aggiornati dell'utente
     * @return ResponseEntity contenente l'utente aggiornato
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id") // Admin o l'utente stesso possono aggiornare i dati
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRole(userDetails.getRole());
            user.setEmail(userDetails.getEmail());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setDateOfBirth(userDetails.getDateOfBirth());
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un utente esistente.
     *
     * @param id l'ID dell'utente da eliminare
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id") // Admin o l'utente stesso possono eliminare l'account
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Richiede il reset della password per un utente.
     *
     * @param payload un Map contenente il nome utente
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Genera un token di reset della password
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setResetTokenExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // 1 ora di validità
            userRepository.save(user);

            // Invia un'email all'utente
            String resetUrl = "http://localhost:8080/api/users/reset-password-confirm?token=" + token;
            sendResetEmail(user.getEmail(), resetUrl);

            return ResponseEntity.ok("Password reset email sent successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Invia un'email di reset della password.
     *
     * @param to l'indirizzo email del destinatario
     * @param resetUrl il link di reset della password
     */
    private void sendResetEmail(String to, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:\n" + resetUrl);
        mailSender.send(message);
    }

    /**
     * Mostra il modulo di reset della password se il token è valido.
     *
     * @param token il token di reset della password
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @GetMapping("/reset-password-confirm")
    public ResponseEntity<?> showResetPasswordForm(@RequestParam("token") String token) {
        Optional<User> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isPresent() && optionalUser.get().getResetTokenExpiryDate().after(new Date())) {
            // Token valido, mostra il modulo per reimpostare la password
            return ResponseEntity.ok("Token is valid. Show reset password form.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    /**
     * Conferma il reset della password se il token è valido.
     *
     * @param token il token di reset della password
     * @param payload un Map contenente la nuova password
     * @return ResponseEntity che indica il risultato dell'operazione
     */
    @PostMapping("/reset-password-confirm")
    public ResponseEntity<?> confirmResetPassword(@RequestParam("token") String token, @RequestBody Map<String, String> payload) {
        Optional<User> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isPresent() && optionalUser.get().getResetTokenExpiryDate().after(new Date())) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(payload.get("password")));
            user.setResetToken(null);
            user.setResetTokenExpiryDate(null);
            userRepository.save(user);
            return ResponseEntity.ok("Password reset successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }
}

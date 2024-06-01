package com.localshop.controllers;

import com.localshop.models.User;
import com.localshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Verifica se il nome utente esiste già
        if (userRepository.existsByUsername(user.getUsername())) {
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

    /**
     * Aggiorna un utente esistente.
     *
     * @param id l'ID dell'utente da aggiornare
     * @param userDetails i dettagli aggiornati dell'utente
     * @return ResponseEntity contenente l'utente aggiornato
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRole(userDetails.getRole());
            user.setEmail(userDetails.getEmail());
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
package com.localshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/register")
    public String register() {
        return "register"; // Questo è il nome del file HTML senza l'estensione .html
    }
}

package org.example.tapahtuma.controllerit;


import org.example.tapahtuma.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.tapahtuma.models.User;// Tämä import varmistaa oman luokkasi käytön
import org.example.tapahtuma.models.Event; // Tämä import varmistaa oman luokkasi käytön
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Näytä rekisteröintisivu
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Viittaa register.html-tiedostoon
    }

    // Käsittele rekisteröintilomake
    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        // Tarkista, onko käyttäjä jo olemassa
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Käyttäjä on jo olemassa!");
            return "register"; // Palaa takaisin rekisteröintisivulle
        }


        try {
            User user = new User(name, email, passwordEncoder.encode(password));
            userRepository.save(user); // Yritetään tallentaa käyttäjä
            System.out.println("Tallennettu käyttäjä: " + user);
        } catch (Exception e) {
            System.err.println("Virhe käyttäjää tallennettaessa: " + e.getMessage());
            e.printStackTrace(); // Näytä virheen tarkemmat tiedot
            model.addAttribute("error", "Rekisteröinti epäonnistui. Yritä uudelleen.");
            return "register";
        }

        // Uudelleenohjaus kirjautumissivulle
        return "redirect:/login";
    }

    // Login-sivu
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Viittaa login.html-tiedostoon
    }

    // Index-sivu
    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        // Hae kirjautuneen käyttäjän tiedot Spring Securityn Principalista
        String email = principal.getName();

        // Hae tietokannan käyttäjä tiedot sähköpostin perusteella
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Käyttäjää ei löytynyt"));

        // Testitapahtumat (esimerkkinä)
        List<Event> events = List.of(
                new Event("Pallollinen harjoitus", "2024-12-03"),
                new Event("Kuntotesti", "2024-12-10")
        );

        // Lisää tiedot malliin
        model.addAttribute("username", user.getUsername()); // Käyttäjänimi tietokannasta
        model.addAttribute("events", events); // Tapahtumat

        return "index"; // Näytä index.html
    }

    @GetMapping("/logout")
    public String showLogoutPage() {
        return "redirect:/login";
    }

}


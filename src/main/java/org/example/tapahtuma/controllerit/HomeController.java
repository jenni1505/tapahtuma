package org.example.tapahtuma.controllerit;


import org.example.tapahtuma.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        if (!userService.registerUser(name, email, password)) {
            model.addAttribute("error", "User already exists or registration failed!");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/logout")
    public String handleLogout() {
        return "login"; // Ohjaa takaisin kirjautumissivulle
    }


    @GetMapping("/calendar")
        public String showCalendarPage() {
            return "calendar"; // Palauttaa templates/calendar.html
        }
    }


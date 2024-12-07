package org.example.tapahtuma.services;

import org.example.tapahtuma.models.User;
import org.example.tapahtuma.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Konstruktorin kautta injektointi
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Hae käyttäjä sähköpostin perusteella
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Käyttäjää ei löytynyt: " + email));
    }

    // Rekisteröi uusi käyttäjä
    public boolean registerUser(String name, String email, String password) {
        // Tarkista, onko käyttäjä jo olemassa
        if (userRepository.findByEmail(email).isPresent()) {
            return false; // Käyttäjä on jo olemassa
        }
        // Salaa salasana ja tallenna käyttäjä
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword);
        userRepository.save(user); // Tallenna käyttäjä
        return true; // Rekisteröinti onnistui
    }

    // Palauta käyttäjän nimi sähköpostin perusteella
    public String getName(String email) {
        return userRepository.findByEmail(email)
                .map(User::getName)
                .orElseThrow(() -> new RuntimeException("Käyttäjää ei löytynyt: " + email));
    }

    // Luo käyttäjä ja tallenna tietokantaan (boolen palautus)
    public boolean createUser(String name, String email, String password) {
        try {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Sähköpostiosoite on jo käytössä: " + email);
            }
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password)); // Salaa salasana
            userRepository.save(newUser); // Tallenna tietokantaan
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Palauta false, jos tallennuksessa ilmenee virhe
        }
    }
}

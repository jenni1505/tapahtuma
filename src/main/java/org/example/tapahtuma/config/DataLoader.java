
package org.example.tapahtuma.config;

import org.example.tapahtuma.models.Event;
import org.example.tapahtuma.models.User;
import org.example.tapahtuma.repositories.EventRepository;
import org.example.tapahtuma.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public DataLoader(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Luo testikäyttäjä
        // Luo testikäyttäjä
        User user = new User();
        user.setUsername("Pelle Pelaaja");
        user.setEmail("kayttaja@example.com");

// Salataan salasana
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("salasana");
        user.setPassword(encodedPassword);

// Tallennetaan käyttäjä tietokantaan
        userRepository.save(user);


        // Luo testitapahtumat
        Event event1 = new Event();
        event1.setNimi("Pallollinen harjoitus");
        event1.setPaivamaara("2024-12-03");
        event1.setCreator(user);

        Event event2 = new Event();
        event2.setNimi("Kuntotesti");
        event2.setPaivamaara("2024-12-10");
        event2.setCreator(user);

        eventRepository.save(event1);
        eventRepository.save(event2);
    }
}


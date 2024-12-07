
package org.example.tapahtuma.config;

import org.example.tapahtuma.models.Event;
import org.example.tapahtuma.models.User;
import org.example.tapahtuma.repositories.EventRepository;
import org.example.tapahtuma.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Luo ensimmäinen testikäyttäjä
        User user1 = new User();
        user1.setName("Pelle Pelaaja");
        user1.setEmail("kayttaja@example.com");
        user1.setPassword(passwordEncoder.encode("salasana"));
        userRepository.save(user1);

        // Luo toinen testikäyttäjä
        User user2 = new User();
        user2.setName("Tiina Treenaaja");
        user2.setEmail("tiina@example.com");
        user2.setPassword(passwordEncoder.encode("toinensalasana"));
        userRepository.save(user2);

        // Luo ensimmäiselle käyttäjälle henkilökohtaiset tapahtumat
        Event event1 = new Event();
        event1.setName("Pallollinen pienryhmäharjoitus");
        event1.setDate(LocalDate.parse("2024-12-30"));
        event1.setCreator(user1);
        event1.setTeamEvent(false);

        Event event2 = new Event();
        event2.setName("Kuntotesti");
        event2.setDate(LocalDate.parse("2024-12-15"));
        event2.setCreator(user1);
        event2.setTeamEvent(false);

        // Luo toiselle käyttäjälle henkilökohtaiset tapahtumat
        Event event3 = new Event();
        event3.setName("Kehonhuolto");
        event3.setDate(LocalDate.parse("2024-12-25"));
        event3.setCreator(user2);
        event3.setTeamEvent(false);

        Event event4 = new Event();
        event4.setName("Voimaharjoittelu");
        event4.setDate(LocalDate.parse("2024-12-28"));
        event4.setCreator(user2);
        event4.setTeamEvent(false);

        // Luo joukkueen yhteiset tapahtumat
        Event teamEvent1 = new Event();
        teamEvent1.setName("Joukkueen kokous");
        teamEvent1.setDate(LocalDate.parse("2024-12-20"));
        teamEvent1.setTeamEvent(true); // Joukkueen tapahtuma
        teamEvent1.setCreator(null);  // Joukkutapahtumalla ei tarvitse olla tiettyä luojaa

        Event teamEvent2 = new Event();
        teamEvent2.setName("Turnaus");
        teamEvent2.setDate(LocalDate.parse("2024-12-13"));
        teamEvent2.setTeamEvent(true); // Joukkueen tapahtuma
        teamEvent2.setCreator(null);

        // Tallenna tapahtumat tietokantaan
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);
        eventRepository.save(teamEvent1);
        eventRepository.save(teamEvent2);

        System.out.println("Testidata luotu!");
    }
}

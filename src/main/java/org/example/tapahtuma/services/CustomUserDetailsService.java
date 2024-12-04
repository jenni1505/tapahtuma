package org.example.tapahtuma.services;

import org.example.tapahtuma.models.User;
import org.example.tapahtuma.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Etsi käyttäjä sähköpostin perusteella
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Emailia ei löytynyt: " + email));

        // Palauta Spring Securityn UserDetails-olio
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // Käytetään sähköpostia tunnisteena
                user.getPassword(),  // Salasana
                List.of(new SimpleGrantedAuthority("ROLE_USER")) // Käyttäjärooli
        );
    }
}



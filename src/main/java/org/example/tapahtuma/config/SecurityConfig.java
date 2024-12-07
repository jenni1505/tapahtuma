package org.example.tapahtuma.config;

import org.example.tapahtuma.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Salli pääsy julkisille resursseille
                        .requestMatchers("/css/**", "/js/**", "/register", "/h2-console/**").permitAll()

                        // REST API -reitit, kuten tapahtumien haku ja käsittely
                        .requestMatchers("/api/events/**").authenticated()

                        // Lomakepohjaiset reitit tapahtumien lisäykseen ja hallintaan
                        .requestMatchers("/events/**").authenticated()

                        // Kaikki muut reitit vaativat kirjautumisen
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        // Poista CSRF-suojaus H2-konsolilta ja REST API -reiteiltä
                        .ignoringRequestMatchers("/h2-console/**", "/api/events/**")
                )
                .headers(headers -> headers
                        // Salli H2-konsolin käyttö
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                .formLogin(form -> form
                        .loginPage("/login") // Kirjautumissivun osoite
                        .usernameParameter("email") // Käyttäjätunnuksena käytetään sähköpostia
                        .passwordParameter("password") // Salasana-kenttä
                        .defaultSuccessUrl("/events", true) // Uudelleenohjaus kirjautumisen jälkeen
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Kirjautumisesta ulos kirjautumisen polku
                        .logoutSuccessUrl("/login") // Uudelleenohjaus kirjautumissivulle
                        .permitAll()
                );

        return http.build();
    }
}


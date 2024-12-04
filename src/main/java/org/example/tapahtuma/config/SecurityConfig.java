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
                        .requestMatchers( "/css/**", "/register", "/h2-console/**").permitAll() // Salli pääsy julkisille sivuille
                        .anyRequest().authenticated() // Suojaa muut URL-polut
                )

                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**")// Poista CSRF vain H2-konsolilta
                )

                .headers(headers -> headers
                    .frameOptions(frameOptions -> frameOptions
                        .disable() // Poista X-Frame-Options, jotta H2-konsoli toimii
                    )
                )

                .formLogin(form -> form
                        .loginPage("/login") // Osoite, jossa kirjautumissivu sijaitsee
                        .usernameParameter("email") // Määritä, että kirjautumisessa käytetään 'email'-kenttää
                        .passwordParameter("password") // Määritä, että kirjautumisessa käytetään 'password'-kenttää
                        .defaultSuccessUrl("/index", true) // Uudelleenohjaus kirjautumisen jälkeen
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Logout-polku
                        .logoutSuccessUrl("/login?logout") // Uudelleenohjaus kirjautumissivulle
                        .permitAll()
                );



        return http.build();
    }
}


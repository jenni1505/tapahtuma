package org.example.tapahtuma.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data // Luo automaattisesti getterit, setterit, toString, equals, ja hashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"USER\"") // H2:n varattu avainsana
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    // Viittaus tapahtumiin, joissa käyttäjä on luoja
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();


    // Kolmen parametrin konstruktori
    public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }

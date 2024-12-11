package org.example.tapahtuma.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // Luo automaattisesti getterit, setterit, toString, equals, ja hashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"USER\"") // H2:n varattu avainsana
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @ToString.Exclude
    // Viittaus tapahtumiin, joissa käyttäjä on luoja
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();


    // Kolmen parametrin konstruktori
    public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

       // Vältä rekursiivisia viittauksia toStringissä
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    }

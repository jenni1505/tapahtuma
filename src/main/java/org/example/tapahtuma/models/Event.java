package org.example.tapahtuma.models;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Event {
    // Tietokantataulun sarake
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nimi;
    private String paivamaara;

    @ManyToOne
    @JoinColumn(name = "creator_id") // Luo viiteavain käyttäjän ID:hen
    private User creator;

    // Getterit, setterit ja konstruktori
    public Event(String nimi, String paivamaara) {
        this.nimi = nimi;
        this.paivamaara = paivamaara;
    }
}

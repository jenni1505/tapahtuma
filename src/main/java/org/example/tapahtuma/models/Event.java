package org.example.tapahtuma.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Name of the event

    @Column(nullable = false)
    private LocalDate date; // Event date

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = true) // Nullable for team events
    private User creator; // Creator of the event

    @Column(nullable = false)
    private boolean teamEvent; // True = team event, False = personal event

    // Avoid recursive references in toString
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", teamEvent=" + teamEvent +
                '}';
    }
}

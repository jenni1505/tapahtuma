package org.example.tapahtuma.repositories;



import org.example.tapahtuma.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Voit lisätä tarvittaessa mukautettuja kyselyitä myöhemmin
}

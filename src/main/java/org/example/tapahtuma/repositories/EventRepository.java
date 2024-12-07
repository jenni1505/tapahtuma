package org.example.tapahtuma.repositories;



import org.example.tapahtuma.models.Event;
import org.example.tapahtuma.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreator(User creator);

    List<Event> findByTeamEventTrue();
}

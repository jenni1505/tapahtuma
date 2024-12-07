package org.example.tapahtuma.mappers;

import org.example.tapahtuma.dto.EventDTO;
import org.example.tapahtuma.models.Event;
import org.example.tapahtuma.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EventMapper {

    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDate().toString(), // LocalDate → String
                event.isTeamEvent(),
                event.getCreator() != null ? event.getCreator().getEmail() : null
        );
    }

    public Event toEntity(EventDTO eventDTO, User creator) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDate(LocalDate.parse(eventDTO.getDate())); // String → LocalDate
        event.setTeamEvent(eventDTO.isTeamEvent());
        event.setCreator(creator);
        return event;
    }
}


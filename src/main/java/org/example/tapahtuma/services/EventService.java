package org.example.tapahtuma.services;

import org.example.tapahtuma.dto.EventDTO;

import org.example.tapahtuma.mappers.EventMapper;
import org.example.tapahtuma.models.Event;
import org.example.tapahtuma.models.User;
import org.example.tapahtuma.repositories.EventRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, UserService userService, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.eventMapper = eventMapper;
    }

    // Hae kaikki tapahtumat
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toDTO) // Käytä EventMapperia
                .collect(Collectors.toList());
    }

    // Hae käyttäjän henkilökohtaiset tapahtumat
    public List<EventDTO> getUserEvents(String userEmail) {
        User user = userService.findUserByEmail(userEmail); // Delegoitu UserServiceen
        return eventRepository.findByCreator(user)
                .stream()
                .map(eventMapper::toDTO) // Käytä EventMapperia
                .collect(Collectors.toList());
    }

    // Hae kaikki joukkutapahtumat
    public List<EventDTO> getTeamEvents() {
        return eventRepository.findByTeamEventTrue()
                .stream()
                .map(eventMapper::toDTO) // Käytä EventMapperia
                .collect(Collectors.toList());
    }

    // Tallenna tapahtuma
    public void saveEvent(EventDTO eventDTO, String userEmail) {
        User user = userService.findUserByEmail(userEmail); // Delegoitu UserServiceen
        Event event = eventMapper.toEntity(eventDTO, user); // Käytä EventMapperia
        eventRepository.save(event);
    }

    // Poista tapahtuma
    public void deleteEvent(Long eventId, String userEmail) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löytynyt"));
        if (!event.getCreator().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Ei oikeuksia poistaa tätä tapahtumaa");
        }
        eventRepository.delete(event);
    }

    public EventDTO getEventForEditing(Long eventId, String userEmail) {
        // Hae tapahtuma ID:n perusteella
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löytynyt"));

        // Tarkista, että käyttäjällä on oikeus muokata tapahtumaa
        if (!event.isTeamEvent() && !event.getCreator().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Sinulla ei ole oikeuksia muokata tätä tapahtumaa.");
        }

        // Palauta tapahtuma DTO:nä
        return eventMapper.toDTO(event);
    }

    public void updateEvent(Long eventId, EventDTO eventDTO, String userEmail) {
        // Hae olemassa oleva tapahtuma
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löytynyt"));

        // Tarkista, että käyttäjällä on oikeus päivittää tapahtumaa
        if (!event.isTeamEvent() && !event.getCreator().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Sinulla ei ole oikeuksia päivittää tätä tapahtumaa.");
        }

        // Päivitä tapahtuman tiedot
        event.setName(eventDTO.getName());
        event.setDate(LocalDate.parse(eventDTO.getDate()));
        event.setTeamEvent(eventDTO.isTeamEvent());

        // Tallenna päivitetty tapahtuma
        eventRepository.save(event);
    }

}

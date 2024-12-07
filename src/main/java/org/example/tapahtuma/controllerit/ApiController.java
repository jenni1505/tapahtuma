package org.example.tapahtuma.controllerit;

import org.example.tapahtuma.dto.EventDTO;
import org.example.tapahtuma.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class ApiController {

    private final EventService eventService;

    public ApiController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Map<String, Object>> getAllEvents(Principal principal) {
        List<Map<String, Object>> events = new ArrayList<>();

        // Käyttäjän omat tapahtumat
        List<EventDTO> userEvents = eventService.getUserEvents(principal.getName());
        userEvents.forEach(event -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", event.getId());
            map.put("title", event.getName());
            map.put("start", event.getDate()); // ISO8601 muoto
            events.add(map);
        });

        // Joukkutapahtumat
        List<EventDTO> teamEvents = eventService.getTeamEvents();
        teamEvents.forEach(event -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", event.getId());
            map.put("title", event.getName());
            map.put("start", event.getDate()); // ISO8601 muoto
            events.add(map);
        });

        return events;
    }

    @PostMapping
    public ResponseEntity<Void> addEvent(@RequestBody EventDTO eventDTO, Principal principal) {
        eventService.saveEvent(eventDTO, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO, Principal principal) {
        eventService.updateEvent(id, eventDTO, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}

package org.example.tapahtuma.controllerit;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.tapahtuma.dto.EventDTO;
import org.example.tapahtuma.services.EventService;
import java.security.Principal;
import org.example.tapahtuma.services.UserService;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping
    public String getEvents(Model model, Principal principal) {
        model.addAttribute("name", userService.getName(principal.getName()));
        model.addAttribute("userEvents", eventService.getUserEvents(principal.getName()));
        model.addAttribute("teamEvents", eventService.getTeamEvents());
        return "index"; // Palauttaa Thymeleaf-näkymän "index.html"
    }

    @GetMapping("/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new EventDTO());
        return "add-event"; // Palauttaa Thymeleaf-näkymän "add-event.html"
    }

    @PostMapping("/add-form")
    public String addEventFromForm(@ModelAttribute EventDTO eventDTO, Principal principal) {
        eventService.saveEvent(eventDTO, principal.getName());
        return "redirect:/events"; // Uudelleenohjaus tapahtumalistaussivulle
    }

    @GetMapping("/{id}/edit")
    public String editEventPage(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("event", eventService.getEventForEditing(id, principal.getName()));
        return "edit-event"; // Palauttaa Thymeleaf-näkymän "edit-event.html"
    }

    @PostMapping("/{id}/edit")
    public String editEvent(@PathVariable Long id, @ModelAttribute EventDTO eventDTO, Principal principal) {
        eventService.updateEvent(id, eventDTO, principal.getName());
        return "redirect:/events";
    }

    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, Principal principal) {
        eventService.deleteEvent(id, principal.getName());
        return "redirect:/events";
    }

    @GetMapping("/calendar")
    public String showCalendarPage() {
        return "calendar"; // Palauttaa kalenterinäkymän
    }
}


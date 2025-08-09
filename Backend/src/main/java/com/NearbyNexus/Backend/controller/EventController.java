package com.NearbyNexus.Backend.controller;

import com.NearbyNexus.Backend.dto.EventDTO;
import com.NearbyNexus.Backend.entity.Event;
import com.NearbyNexus.Backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO savedDTO = eventService.createEvent(eventDTO);
        return ResponseEntity.ok(savedDTO);
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getEvents(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(eventService.getEvents(city, category));
    }
}

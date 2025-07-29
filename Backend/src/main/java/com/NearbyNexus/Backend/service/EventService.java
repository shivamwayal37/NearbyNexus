package com.NearbyNexus.Backend.service;

import com.NearbyNexus.Backend.dto.EventDTO;
import com.NearbyNexus.Backend.entity.Event;
import com.NearbyNexus.Backend.entity.User;
import com.NearbyNexus.Backend.repository.EventRepository;
import com.NearbyNexus.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public Event createEvent(EventDTO eventDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User organizer = userRepository.findByEmail(email);
        if (organizer == null || !organizer.getRole().equals(User.Role.ORGANIZER)) {
            throw new RuntimeException("Only organizers can create events");
        }

        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        event.setCategory(eventDTO.getCategory());
        event.setOrganizer(organizer);
        return eventRepository.save(event);
    }

    @Cacheable(value = "events", key = "#city + ':' + #category")
    public List<EventDTO> getEvents(String city, String category) {
        List<Event> events;
        if (city != null && category != null) {
            events = eventRepository.findByLocationContainingIgnoreCaseAndCategory(city, category);
        } else if (city != null) {
            events = eventRepository.findByLocationContainingIgnoreCase(city);
        } else if (category != null) {
            events = eventRepository.findByCategory(category);
        } else {
            events = eventRepository.findAll();
        }
        return events.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setDate(event.getDate());
        dto.setLocation(event.getLocation());
        dto.setLatitude(event.getLatitude());
        dto.setLongitude(event.getLongitude());
        dto.setCategory(event.getCategory());
        return dto;
    }
}

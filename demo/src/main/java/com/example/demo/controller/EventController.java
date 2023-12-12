package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Event;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/events")
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/get")
    public List<Event> list() {
        return eventService.listAllEvent();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Event> get(@PathVariable Integer id) {
        try {
            Event user = eventService.getEvent(id);
            return new ResponseEntity<Event>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getEventByUserId")
    public List<Event> listEventCreatedByUser() {
        Integer id = userRepository.findByUsername(getContext().getAuthentication().getName()).get().getId();
        return eventService.listAllEvent().stream().filter(x -> Objects.equals(x.getUserId(),id )).collect(toList());
    }

    @PostMapping("")
    public void add(@RequestBody Event event) {
        UserEntity user = userRepository.findByUsername(getContext().getAuthentication().getName()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        event.setUserId(user.getId());
        eventService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Event event, @PathVariable Integer id) {

        try {
            eventRepository.findById(id).ifPresent(x -> {
                x.setNameEvent(event.getNameEvent());
                x.setDescription(event.getDescription());
                eventRepository.save(x);
            });
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        eventService.deleteEvent(id);
    }


}
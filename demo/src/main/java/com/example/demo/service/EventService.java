package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> listAllEvent() {
        return eventRepository.findAll();
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);

    }

    public Event getEvent(Integer id) {
        return eventRepository.findById(id).get();
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }
}
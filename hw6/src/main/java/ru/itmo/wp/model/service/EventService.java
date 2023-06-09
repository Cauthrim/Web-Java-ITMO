package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepositoryImpl eventRepository = new EventRepositoryImpl();

    public void addEvent(Event event) {
        eventRepository.save(event);
    }
}

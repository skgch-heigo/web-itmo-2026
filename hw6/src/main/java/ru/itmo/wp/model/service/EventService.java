package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.EventType;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void saveEnter(User user) {
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(EventType.ENTER);
        eventRepository.save(event);
    }

    public void saveLogout(User user) {
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(EventType.LOGOUT);
        eventRepository.save(event);
    }
}

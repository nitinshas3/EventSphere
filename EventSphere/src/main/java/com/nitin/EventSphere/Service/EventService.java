package com.nitin.EventSphere.Service;

import java.util.Optional;
import java.util.UUID;

import com.nitin.EventSphere.domain.CreateEventRequest;
import com.nitin.EventSphere.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);


}

// page is like dividing db in some partitions rather than loading all the rows at once , which happens in case we use list  we will only get partitions this is called pagination 10 pages 20 pages etc


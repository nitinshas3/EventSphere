package com.nitin.EventSphere.Service;

import java.util.Optional;
import java.util.UUID;

import com.nitin.EventSphere.domain.CreateEventRequest;
import com.nitin.EventSphere.domain.UpdateEventRequest;
import com.nitin.EventSphere.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);

    Event updateEventForOrganizer(UUID organizerId , UUID id , UpdateEventRequest updateEventRequest);

    void deleteEventForOrganizer(UUID organizerId, UUID id);

    Page<Event> listPublishedEvents(Pageable pageable);

    Page<Event> searchPublishedEvents(String query, Pageable pageable);

    Optional<Event> getPublishedEvent(UUID id);
}

// page is like dividing db in some partitions rather than loading all the rows at once , which happens in case we use list  we will only get partitions this is called pagination 10 pages 20 pages etc

// createevent() -> creates event

// listeventsfororganizer() -> lists events of particular organizer one to many relationship

//getEventForOrganizer() -> gets a specific event

// updateeventfororganizer() -> updates a specific event

//listPublichsedEvents() -> lsiting events available for booking , all the events publisehd

//searchpublishedevents() -> this lets us search the events based on some queries like venue location , ticket type etc

//getpublishedEvent() -> gets a single event detials which is published and this can be shown to all even withtout auth , so this will be in published event controller , whihc has permit all () , in this we can book tickets and all
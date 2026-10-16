package com.nitin.EventSphere.controller;

import com.nitin.EventSphere.Service.EventService;
import com.nitin.EventSphere.domain.CreateEventRequest;
import com.nitin.EventSphere.domain.UpdateEventRequest;
import com.nitin.EventSphere.domain.dto.*;
import com.nitin.EventSphere.domain.entities.Event;
import com.nitin.EventSphere.mappers.EventMapper;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nitin.EventSphere.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt, //this annotation gives the recent autehticated objects principal , which has to be of type jwt , stored in serucity context
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ) {
        UUID userId = parseUserId(jwt);
        Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);
        return ResponseEntity.ok(
                events.map(event -> eventMapper.toListEventResponseDto(event))
        );
    }


    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);
        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event updatedEvent = eventService.updateEventForOrganizer(
                userId, eventId, updateEventRequest
        );

        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(
                updatedEvent);

        return ResponseEntity.ok(updateEventResponseDto);
    }


    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);
        eventService.deleteEventForOrganizer(userId, eventId);
        return ResponseEntity.noContent().build();
    }




} // createEvent() -> takes jwt and that users id is sent to dto , that will become the organizer and the presentation layer dto is received in the request it is converted to service layer dto using mapper

//in listevents() -> , we use page for pagination , and it has inbuilt map method so we do not requrie stream unlike if we used list , so map converts one form to another in here converst event to eventresponsedto using mapper , and that pageable object is injected automatically by spring

// listevent() -> the pages will be used like when we see page 1 page 2 , in websites rather than loading entire thing , like contest rankings etc

//listEvents() -> this just lists the event the current user is related to not all , like the current user is the one managing it or he is the organizer only those events

//getEvent()-> this gives details about a particular event gives listeventresponsedto which has listeventtickettyperesponsedto

//updateevent()-> updates the event , takes updateeventrequestdto with updateticketytyperequestdto in it , and returns updateeventresponsedto , updates a single event

//delteevent() -> no request response dto , delete event directly


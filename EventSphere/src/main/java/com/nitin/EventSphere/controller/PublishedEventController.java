package com.nitin.EventSphere.controller;

import java.util.UUID;

import com.nitin.EventSphere.Service.EventService;
import com.nitin.EventSphere.domain.dto.GetPublishedEventDetailsResponseDto;
import com.nitin.EventSphere.domain.dto.ListPublishedEventResponseDto;
import com.nitin.EventSphere.domain.entities.Event;
import com.nitin.EventSphere.mappers.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable) {

        Page<Event> events;
        if (null != q && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(
                events.map(eventMapper::toListPublishedEventResponseDto)
        );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails(
            @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

// why this contrller sepearte and not in event controller ? Listing published events is a public-facing operation, whereas creating, updating, and deleting events are typically organizer operations.
//
//Separating them can make the code easier to organize.

// also like those in event controller require organizer to be allowed to do all the work , this controller can be accessed by anyone

// so we have allowrd permit all () to this endpoint in security config coz anyone can see the events published

//listevent and search events 2 differnt methods but used in same controller , we see if any query param sent , if not sent list all the events if send then call the serachevent method

//requestparam gets data from the url query parameter

//last controller lists

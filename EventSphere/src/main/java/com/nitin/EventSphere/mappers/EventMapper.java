package com.nitin.EventSphere.mappers;

import com.nitin.EventSphere.domain.CreateEventRequest;
import com.nitin.EventSphere.domain.CreateTicketTypeRequest;
import com.nitin.EventSphere.domain.dto.CreateEventRequestDto;
import com.nitin.EventSphere.domain.dto.CreateEventResponseDto;
import com.nitin.EventSphere.domain.dto.CreateTicketTypeRequestDto;
import com.nitin.EventSphere.domain.entities.Event;
import com.nitin.EventSphere.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

}
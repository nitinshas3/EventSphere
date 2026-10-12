package com.nitin.EventSphere.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.nitin.EventSphere.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventResponseDto {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<UpdateTicketTypeResponseDto> ticketTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
// this dto is used in giving response in controller for updating the event , the event entity updated is converted to this and sent back updateeventrequestdto -> updateeventrequest -> event -> updateeventresponsedto

package com.nitin.EventSphere.domain.dto;

import java.util.UUID;

import com.nitin.EventSphere.domain.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTicketTypeResponseDto ticketType;
}
// this dto used to list the tickets booked by the user.

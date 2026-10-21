package com.nitin.EventSphere.domain.dto;

import java.util.UUID;

import com.nitin.EventSphere.domain.enums.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {
    private UUID ticketId;
    private TicketValidationStatusEnum status;
}

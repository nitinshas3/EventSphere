package com.nitin.EventSphere.domain.dto;

import java.util.UUID;

import com.nitin.EventSphere.domain.enums.TicketValidationMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
    private UUID id;
    private TicketValidationMethod method;
}

// the uuid can mean qr code id or ticket id based on the validaton type we are using either manual or by qr scan

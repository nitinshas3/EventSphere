package com.nitin.EventSphere.Service;

import com.nitin.EventSphere.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}

// we are doing the purchase ticket function in this coz we create a ticker from a ticket type so logical

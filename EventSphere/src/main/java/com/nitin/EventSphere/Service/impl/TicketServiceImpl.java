package com.nitin.EventSphere.Service.impl;


import java.util.Optional;
import java.util.UUID;

import com.nitin.EventSphere.Service.TicketService;
import com.nitin.EventSphere.domain.entities.Ticket;
import com.nitin.EventSphere.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
//listtickerforuser() -> tickets of a particular user find tickets by that user id

//gettickerforuser() -> one particular ticket of a particular user
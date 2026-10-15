package com.nitin.EventSphere.Service.impl;

import com.nitin.EventSphere.Service.QrCodeService;
import com.nitin.EventSphere.Service.TicketTypeService;
import com.nitin.EventSphere.domain.entities.Ticket;

import java.util.UUID;

import com.nitin.EventSphere.domain.entities.TicketType;
import com.nitin.EventSphere.domain.entities.User;
import com.nitin.EventSphere.domain.enums.TicketStatusEnum;
import com.nitin.EventSphere.exceptions.TicketTypeNotFoundException;
import com.nitin.EventSphere.exceptions.TicketsSoldOutException;
import com.nitin.EventSphere.exceptions.UserNotFoundException;
import com.nitin.EventSphere.repositories.TicketRepository;
import com.nitin.EventSphere.repositories.TicketTypeRepository;
import com.nitin.EventSphere.repositories.UserRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId) // lock here
                .orElseThrow(() -> new TicketTypeNotFoundException(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket); // save the ticket and generate the qr code
        qrCodeService.generateQrCode(savedTicket);

        //return ticketRepository.save(savedTicket);
        return savedTicket;
    }
}

// purchasing the ticket from ticket type , event one to many relation with ticket type , ticket type one to many relation with ticket , to we give ticket type and user id ,


// there is a pesimestic write lock on getting the ticket type , so if one person accesses it unitl transaction commits or rollsback it doenst open the lock , so if the tickettype cannot be retrived the purchase ticket functin wont move forward
package com.nitin.EventSphere.repositories;

import java.util.Optional;
import java.util.UUID;

import com.nitin.EventSphere.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    int countByTicketTypeId(UUID ticketTypeId);

    Page<Ticket> findByPurchaserId(UUID purchaserId, Pageable pageable);

    Optional<Ticket> findByIdAndPurchaserId(UUID id, UUID purchaserId);
}

// countbytickettype id , returns total number of tickets of that ticket type available while purchasing the ticket

//findbypurchaserid used in listing the tickets a user has purchased

// findbyidandpurchaserid -> to get a particular purchased ticket of a user
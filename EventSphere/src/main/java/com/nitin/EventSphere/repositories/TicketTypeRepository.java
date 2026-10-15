package com.nitin.EventSphere.repositories;

import com.nitin.EventSphere.domain.entities.TicketType;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, UUID> {

    @Query("SELECT tt FROM TicketType tt WHERE tt.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TicketType> findByIdWithLock(@Param("id") UUID id);
}
// this is used to get ticket type that needs to be booked in the purhcase ticket function , so when a person accesses a ticket type , that row in the ticket type table get locked , and others cant read or write in that row

// pesimestic write -> doesnt allow both read and write pesimestic read allows read not write
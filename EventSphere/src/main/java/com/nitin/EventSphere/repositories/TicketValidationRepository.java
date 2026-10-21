package com.nitin.EventSphere.repositories;

import java.util.UUID;

import com.nitin.EventSphere.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}

//

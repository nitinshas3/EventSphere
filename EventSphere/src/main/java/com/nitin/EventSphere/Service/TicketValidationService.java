package com.nitin.EventSphere.Service;

import com.nitin.EventSphere.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}

// validateticketbyqrcode is a method which gives ticketvalidation enum , it is used to scan the qrcode and give the enum

// the other one manually method is to enter the ticket id manually and scan the ticket , this is difficult while scaling cant enter uuid easily

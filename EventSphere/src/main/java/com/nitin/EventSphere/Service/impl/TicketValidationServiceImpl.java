package com.nitin.EventSphere.Service.impl;

import com.nitin.EventSphere.Service.TicketValidationService;
import com.nitin.EventSphere.domain.entities.QrCode;
import com.nitin.EventSphere.domain.entities.Ticket;
import com.nitin.EventSphere.domain.entities.TicketValidation;
import com.nitin.EventSphere.domain.enums.QrCodeStatusEnum;
import com.nitin.EventSphere.domain.enums.TicketValidationMethod;
import com.nitin.EventSphere.domain.enums.TicketValidationStatusEnum;
import com.nitin.EventSphere.exceptions.QrCodeNotFoundException;
import com.nitin.EventSphere.exceptions.TicketNotFoundException;
import com.nitin.EventSphere.repositories.QrCodeRepository;
import com.nitin.EventSphere.repositories.TicketRepository;
import com.nitin.EventSphere.repositories.TicketValidationRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional // will be making multiple db calls so
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "QR Code with ID %s was not found", qrCodeId
                        )
                ));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    private TicketValidation validateTicket(Ticket ticket,
                                            TicketValidationMethod ticketValidationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }
}

// the 2 method description mentioned in interface of this check that one manulaly and the first one


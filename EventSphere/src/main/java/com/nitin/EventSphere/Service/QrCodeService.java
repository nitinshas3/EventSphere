package com.nitin.EventSphere.Service;

import com.nitin.EventSphere.domain.entities.QrCode;
import com.nitin.EventSphere.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}

// generateqrcode() -> takes ticket as parameter , the attributes in ticket are converted in a string and hasehd and that is converted to qrcode and in entity that hashed value is stored , when scanning the qrcode the qr is converted to hasehd string adn that is compared with the value in entity to verify

// 2nd one used in
package com.nitin.EventSphere.Service;

import com.nitin.EventSphere.domain.entities.QrCode;
import com.nitin.EventSphere.domain.entities.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

}

// generateqrcode() -> takes ticket as parameter , the attributes in ticket are converted in a string and hasehd and that is converted to qrcode and in entity that hashed value is stored , when scanning the qrcode the qr is converted to hasehd string adn that is compared with the value in entity to verify

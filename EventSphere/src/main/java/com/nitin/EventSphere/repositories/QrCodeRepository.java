package com.nitin.EventSphere.repositories;

import com.nitin.EventSphere.domain.entities.QrCode;
import com.nitin.EventSphere.domain.enums.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaseId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}

// will be used to store and retrive qrcode entity from the db

//findbyticketidandticketpurchase id used to get the qrcode image in the qrcodeservice and show the qrcode image

//findbyidandstatus is used to get the get the qr code while validation , and only get those qrcode which are valid and not expired , this is used in ticketvalidation service

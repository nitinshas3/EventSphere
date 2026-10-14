package com.nitin.EventSphere.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfig {

    @Bean
    public QRCodeWriter qrCodeWriter() {
        return new QRCodeWriter();
    }

}
//A QRCodeWriter bean is a QR-code generator object that Spring manages for your application.

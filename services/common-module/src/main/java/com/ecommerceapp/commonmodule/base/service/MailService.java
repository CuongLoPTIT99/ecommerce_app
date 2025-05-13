package com.ecommerceapp.commonmodule.base.service;

import com.ecommerceapp.commonmodule.dto.MailItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final KafkaTemplate<String, MailItemDTO> kafkaTemplate;

    public void sendMail(MailItemDTO message) {
        // Logic to send notification
        log.info("Sending mail successfully1");
        kafkaTemplate.send("mail", message);
    }
}

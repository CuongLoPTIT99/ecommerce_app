package com.ecommerceapp.commonmodule.base.service;

import com.ecommerceapp.commonmodule.dto.NotificationMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, NotificationMessageDTO> kafkaTemplate;

    public void sendPushNotification(NotificationMessageDTO message) {
        // Logic to send notification
        kafkaTemplate.send("notification", message);
    }
}

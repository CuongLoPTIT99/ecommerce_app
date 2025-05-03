package com.ecommerceapp.commonmodule.base.service;

import com.ecommerceapp.commonmodule.base.dto.NotificationMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
//    private final KafkaTemplate<String, NotificationMessageDTO> kafkaTemplate;
//    private final KafkaTemplate<String, NotificationMessageDTO> kafkaTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate1;

//    public void sendPushNotification(NotificationMessageDTO message) {
//        // Logic to send notification
//        kafkaTemplate.send("notification2", message);
//    }

    public void sendPushNotification(String message) {
        // Logic to send notification
        kafkaTemplate1.send("notification3", message);
    }
}

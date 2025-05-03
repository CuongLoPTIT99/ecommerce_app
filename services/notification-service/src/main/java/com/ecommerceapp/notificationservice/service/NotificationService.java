package com.ecommerceapp.notificationservice.service;

import com.ecommerceapp.notificationservice.model.NotificationMessage;
import com.ecommerceapp.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository repository;

    public void sendNotification(NotificationMessage message) {
        // todo: save to db
        repository.save(message);
        System.out.println("Notification sent: " + message);
//        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}

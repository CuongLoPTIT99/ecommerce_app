package com.ecommerceapp.notificationservice.kafka;

import com.ecommerceapp.commonmodule.dto.NotificationMessageDTO;
import com.ecommerceapp.notificationservice.model.NotificationMessage;
import com.ecommerceapp.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void receiveNotification(NotificationMessageDTO messageJson) {
        NotificationMessage notificationMessage = new NotificationMessage();
        BeanUtils.copyProperties(messageJson, notificationMessage);
        notificationService.sendNotification(notificationMessage);
    }
}

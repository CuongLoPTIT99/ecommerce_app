package com.ecommerceapp.notificationservice.kafka;

import com.ecommerceapp.commonmodule.base.dto.NotificationMessageDTO;
import com.ecommerceapp.notificationservice.model.NotificationMessage;
import com.ecommerceapp.notificationservice.service.NotificationService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

//    @KafkaListener(topics = "notification2", groupId = "notification-group")
//    public void receiveNotification(NotificationMessageDTO messageJson) {
////        NotificationMessageDTO message = gson.fromJson(messageJson.toString(), NotificationMessageDTO.class);
//        NotificationMessage notificationMessage = new NotificationMessage();
//        BeanUtils.copyProperties(messageJson, notificationMessage);
//        notificationService.sendNotification(notificationMessage);
//    }

    @KafkaListener(topics = "notification3")
    public void receiveNotification(String messageJson) {
        System.out.println("Received message: " + messageJson);
    }
}

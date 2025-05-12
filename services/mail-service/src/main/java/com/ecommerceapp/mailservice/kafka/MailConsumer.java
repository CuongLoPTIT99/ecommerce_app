package com.ecommerceapp.mailservice.kafka;

import com.ecommerceapp.commonmodule.base.dto.MailItemDTO;
import com.ecommerceapp.commonmodule.base.dto.NotificationMessageDTO;
import com.ecommerceapp.mailservice.model.MailItem;
import com.ecommerceapp.mailservice.service.MailItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailConsumer {
    private final MailItemService mailItemService;

    @KafkaListener(topics = "mail", groupId = "mail-group")
    public void sendMail2Server(MailItemDTO mailItemDTO) {
        mailItemService.sendPaymentSuccessEmail(
                MailItem.builder()
                        .sendTo(mailItemDTO.getRecipientId())
                        .subject(mailItemDTO.getTitle())
                        .body(mailItemDTO.getContent())
                        .build()
        );
    }
}

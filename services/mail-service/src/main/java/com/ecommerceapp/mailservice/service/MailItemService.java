package com.ecommerceapp.mailservice.service;

import com.ecommerceapp.mailservice.model.MailItem;
import com.ecommerceapp.mailservice.repository.MailItemRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailItemService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final MailItemRepository repository;

    public void sendPaymentSuccessEmail(MailItem mailItem) {
        // save mail item to database
        repository.save(mailItem);

        // process send mail to server
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom("contact@cuongphung.com");

            Map<String, Object> variables = new HashMap<>();
            variables.put("name", "test");

            Context context = new Context();
            context.setVariables(variables);
            helper.setSubject("sendPaymentSuccessEmail");
            String htmlTemplate = templateEngine.process("mail-template.html", context);
            helper.setText(htmlTemplate, true);

            helper.setTo("foreverbelieveinmydream1999@gmail.com");
            mailSender.send(message);
            log.info("Email successfully");
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email");
        }
    }
}

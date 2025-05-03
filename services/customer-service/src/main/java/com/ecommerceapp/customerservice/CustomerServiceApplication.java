package com.ecommerceapp.customerservice;

import com.ecommerceapp.commonmodule.base.dto.NotificationMessageDTO;
import com.ecommerceapp.commonmodule.base.service.NotificationService;
import com.ecommerceapp.commonmodule.constant.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = {
        "com.ecommerceapp.customerservice",
        "com.ecommerceapp.commonmodule.base"
})
public class CustomerServiceApplication implements CommandLineRunner {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final NotificationService notificationService;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scheduler.scheduleAtFixedRate(this::doSomething, 0, 5, TimeUnit.SECONDS);
    }

    private void doSomething() {
        // Your scheduled task logic here
        System.out.println("Scheduled task executed");
        // For example, you can call a method to fetch data from the database or perform some other operation
        // fetchDataFromDatabase();
        // Or you can send a notification using the NotificationService
//        notificationService.sendPushNotification(NotificationMessageDTO.builder()
//                .title("Test Notification")
//                .content("This is a test notification")
//                .recipientId("12345")
//                .status(Enums.NotificationStatus.PENDING)
//                .createdAt(new Timestamp(System.currentTimeMillis()))
//                .build());

        notificationService.sendPushNotification("1lo");
    }
}

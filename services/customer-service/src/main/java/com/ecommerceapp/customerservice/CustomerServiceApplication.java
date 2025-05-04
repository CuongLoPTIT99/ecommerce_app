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
@ComponentScan(basePackages = {
        "com.ecommerceapp.customerservice",
        "com.ecommerceapp.commonmodule"
})
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}

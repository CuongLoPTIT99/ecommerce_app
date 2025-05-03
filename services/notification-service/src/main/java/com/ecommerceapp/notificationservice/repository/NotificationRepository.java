package com.ecommerceapp.notificationservice.repository;

import com.ecommerceapp.notificationservice.model.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationMessage, Long> {
    // Custom query methods can be defined here if needed
}

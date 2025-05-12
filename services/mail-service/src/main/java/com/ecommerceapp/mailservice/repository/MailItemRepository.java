package com.ecommerceapp.mailservice.repository;

import com.ecommerceapp.mailservice.model.MailItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailItemRepository extends JpaRepository<MailItem, Long> {
}

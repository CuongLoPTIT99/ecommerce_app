package com.ecommerceapp.mailservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "mail_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "send_to")
    private String sendTo;
    private String subject;
    private String body;
}

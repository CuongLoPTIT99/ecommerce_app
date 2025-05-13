package com.ecommerceapp.commonmodule.dto;

import com.ecommerceapp.commonmodule.constant.Enums;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailItemDTO implements Serializable {
    private String title;
    private String content;
    private String recipientId;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Enums.NotificationStatus status;
    private Timestamp createdAt;
}

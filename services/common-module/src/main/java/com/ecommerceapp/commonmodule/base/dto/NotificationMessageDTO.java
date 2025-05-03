package com.ecommerceapp.commonmodule.base.dto;

import com.ecommerceapp.commonmodule.constant.Enums;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
public class NotificationMessageDTO implements Serializable {
    private String title;
    private String content;
    private String recipientId;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Enums.NotificationStatus status;
    private Timestamp createdAt;
}

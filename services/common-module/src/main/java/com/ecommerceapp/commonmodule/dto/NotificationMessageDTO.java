package com.ecommerceapp.commonmodule.dto;

import com.ecommerceapp.commonmodule.constant.APIBaseUrlConstant;
import com.ecommerceapp.commonmodule.enums.CommonEnum;
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
public class NotificationMessageDTO implements Serializable {
    private String title;
    private String content;
    private Long recipientId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CommonEnum.NotificationStatus status;
    private Timestamp createdAt;
}

package com.ecommerceapp.commonmodule.enums;

public class CommonEnum {

    public enum NotificationStatus {
        PENDING,
        SENT,
        FAILED
    }

    public enum MailType {
        WELCOME,
        ORDER_CONFIRMATION,
        PASSWORD_RESET,
        NEWSLETTER
    }

    public enum UserRole {
        ADMIN,
        USER,
        GUEST
    }

    public enum ProductCategory {
        ELECTRONICS,
        FASHION,
        HOME_APPLIANCES,
        BOOKS
    }
}

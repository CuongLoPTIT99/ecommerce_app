package com.ecommerceapp.commonmodule.constant;

public class Enums {
    public enum NotificationStatus {
        PENDING(0),
        SENT(1),
        FAILED(2);

        private final int value;

        NotificationStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}

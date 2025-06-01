package com.ecommerceapp.commonmodule.util;

import java.sql.Timestamp;

public class DateTimeUtil {
    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}

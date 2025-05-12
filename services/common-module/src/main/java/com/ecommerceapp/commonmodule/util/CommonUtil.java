package com.ecommerceapp.commonmodule.util;

import java.util.Collection;

public class CommonUtil {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

//    public static int compare(Object o1, Object o2) {
//        if (o1 == null && o2 == null) {
//            return 0;
//        }
//    }

    public static String generateRandomString(int length) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

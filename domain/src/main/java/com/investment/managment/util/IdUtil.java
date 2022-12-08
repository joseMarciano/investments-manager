package com.investment.managment.util;

import java.util.UUID;

public final class IdUtil {

    private IdUtil() {
    }

    public static String unique() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

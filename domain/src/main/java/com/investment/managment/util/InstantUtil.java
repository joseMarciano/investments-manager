package com.investment.managment.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtil {

    private InstantUtil() {
    }

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
}

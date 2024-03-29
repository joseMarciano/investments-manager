package com.investment.managment.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateExecutionRequest(
        String id,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {
}

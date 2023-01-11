package com.investment.managment.api.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateExecutionRequest(
        String id,
        String stockId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {
}

package com.investment.managment.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionRequest(
        String stockId,
        String walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {
}

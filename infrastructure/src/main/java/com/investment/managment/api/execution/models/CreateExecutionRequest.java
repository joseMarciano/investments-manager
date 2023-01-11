package com.investment.managment.api.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionRequest(
        String stockId,
        String walletId,
        Double profitPercentage,
        Long buyExecutedQuantity,
        BigDecimal buyExecutedPrice,
        Instant boughtAt

) {
}

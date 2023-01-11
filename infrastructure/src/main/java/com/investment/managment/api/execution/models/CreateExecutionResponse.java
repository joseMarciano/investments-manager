package com.investment.managment.api.execution.models;

import com.investment.managment.execution.ExecutionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionResponse(
        String id,
        String stockId,
        String walletId,
        Double profitPercentage,
        Long buyExecutedQuantity,
        BigDecimal buyExecutedPrice,
        BigDecimal buyExecutedVolume,
        ExecutionStatus status,
        Instant boughtAt,
        Instant createdAt,
        Instant updatedAt
) {
}

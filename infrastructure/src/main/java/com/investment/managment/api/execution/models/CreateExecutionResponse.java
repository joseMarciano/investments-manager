package com.investment.managment.api.execution.models;

import com.investment.managment.execution.ExecutionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionResponse(
        String id,
        String stockId,
        String walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status,
        Instant executedAt,
        Instant createdAt,
        Instant updatedAt
) {
}

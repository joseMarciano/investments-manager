package com.investment.managment.execution.models;

import com.investment.managment.execution.ExecutionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record FindByIdExecutionResponse(
        String id,
        String origin,
        String stockId,
        String walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status,
        Instant executedAt,
        Instant createdAt,
        Instant updatedAt) {
}

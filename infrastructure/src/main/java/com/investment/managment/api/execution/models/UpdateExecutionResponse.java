package com.investment.managment.api.execution.models;

import com.investment.managment.execution.ExecutionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateExecutionResponse(
        String id,
        String stockId,
        String walletId,
        Double profitPercentage,
        Long buyExecutedQuantity,
        BigDecimal buyExecutedPrice,
        BigDecimal buyExecutedVolume,
        Long sellExecutedQuantity,
        BigDecimal sellExecutedPrice,
        BigDecimal sellExecutedVolume,
        ExecutionStatus status,
        Instant boughtAt,
        Instant soldAt,
        Instant createdAt,
        Instant updatedAt
) {
}

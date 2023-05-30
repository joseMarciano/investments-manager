package com.investment.managment.execution.models;

import com.investment.managment.execution.ExecutionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PageExecutionResponse(
        String id,
        String originId,
        String stockId,
        String walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status,
        BigDecimal pnlOpen,
        BigDecimal pnlOpenPercentage,
        BigDecimal pnlClose,
        BigDecimal pnlClosePercentage,
        Instant executedAt,
        Instant createdAt,
        Instant updatedAt
) {
}

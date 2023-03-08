package com.investment.managment.execution.models;

public record SummaryExecutionByStockResponse(
        String stockId,
        String symbol,
        Long totalQuantity,
        Long totalSoldQuantity,
        Long totalCustodyQuantity
) {
}

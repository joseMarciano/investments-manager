package com.investment.managment.execution.models;

import java.math.BigDecimal;

public record SummaryExecutionByStockResponse(
        String stockId,
        String symbol,
        Long totalQuantity,
        Long totalSoldQuantity,
        Long totalCustodyQuantity,
        BigDecimal totalPnlClose,
        BigDecimal totalPnlOpen
) {
}

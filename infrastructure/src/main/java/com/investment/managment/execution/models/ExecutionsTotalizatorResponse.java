package com.investment.managment.execution.models;

import java.math.BigDecimal;

public record ExecutionsTotalizatorResponse(
        Long totalSoldQuantity,
        Long totalBoughtQuantity,
        BigDecimal totalPnlOpen,
        BigDecimal totalPnlClose
) {
}

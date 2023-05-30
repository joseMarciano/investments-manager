package com.investment.managment.execution.models;

import java.math.BigDecimal;

public record ExecutionPnlRequest(
        String id,
        BigDecimal pnl,
        BigDecimal pnlOpenPercentage

) {
}

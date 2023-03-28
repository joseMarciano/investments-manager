package com.investment.managment.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record SellExecutionRequest(
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal stockSoldPrice,
        Instant executedAt

) {
}

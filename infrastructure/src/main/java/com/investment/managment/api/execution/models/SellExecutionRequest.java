package com.investment.managment.api.execution.models;

import java.math.BigDecimal;
import java.time.Instant;

public record SellExecutionRequest(
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {
}

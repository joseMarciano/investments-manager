package com.investment.managment.execution.sell;

import com.investment.managment.execution.ExecutionID;

import java.math.BigDecimal;
import java.time.Instant;

public record SellExecutionCommandInput(
        ExecutionID originId,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal stockSoldPrice,
        Instant executedAt

) {

    public static SellExecutionCommandInput with(
            final ExecutionID originId,
            final Long executedQuantity,
            final BigDecimal executedPrice,
            final BigDecimal stockSoldPrice,
            final Instant executedAt) {
        return new SellExecutionCommandInput(originId, executedQuantity, executedPrice, stockSoldPrice, executedAt);
    }

}

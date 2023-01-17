package com.investment.managment.execution.update.buy;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateBuyExecutionCommandInput(
        String id,
        String stockId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {


    public static UpdateBuyExecutionCommandInput with(final String id, final String stockId, final Long executedQuantity, final BigDecimal executedPrice, final Double profitPercentage, final Instant executedAt) {
        return new UpdateBuyExecutionCommandInput(id, stockId, profitPercentage, executedQuantity, executedPrice, executedAt);
    }
}

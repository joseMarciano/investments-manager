package com.investment.managment.execution.update;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateExecutionCommandInput(

        String id,
        String stockId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {


    public static UpdateExecutionCommandInput with(final String id, final String stockId, final Long executedQuantity, final BigDecimal executedPrice, final Double profitPercentage, final Instant executedAt) {
        return new UpdateExecutionCommandInput(id, stockId, profitPercentage, executedQuantity, executedPrice, executedAt);
    }
}

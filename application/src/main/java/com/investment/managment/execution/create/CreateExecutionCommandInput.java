package com.investment.managment.execution.create;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionCommandInput(
        String stockId,
        String walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        Instant executedAt

) {

    public static CreateExecutionCommandInput with(final String stockId,
                                                   final String walletId,
                                                   final Double profitPercentage,
                                                   final Long executedQuantity,
                                                   final BigDecimal executedPrice,
                                                   final Instant executedAt) {
        return new CreateExecutionCommandInput(stockId, walletId, profitPercentage, executedQuantity, executedPrice, executedAt);
    }

}

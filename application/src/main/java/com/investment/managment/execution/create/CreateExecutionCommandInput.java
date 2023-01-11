package com.investment.managment.execution.create;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateExecutionCommandInput(
        String stockId,
        String walletId,
        Double profitPercentage,
        Long buyExecutedQuantity,
        BigDecimal buyExecutedPrice,
        Instant boughtAt

) {

    public static CreateExecutionCommandInput with(final String stockId,
                                                   final String walletId,
                                                   final Double profitPercentage,
                                                   final Long buyExecutedQuantity,
                                                   final BigDecimal buyExecutedPrice,
                                                   final Instant boughtAt) {
        return new CreateExecutionCommandInput(stockId, walletId, profitPercentage, buyExecutedQuantity, buyExecutedPrice, boughtAt);
    }

}

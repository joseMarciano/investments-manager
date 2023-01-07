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

}

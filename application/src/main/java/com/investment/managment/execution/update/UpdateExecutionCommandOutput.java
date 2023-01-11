package com.investment.managment.execution.update;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateExecutionCommandOutput(
        ExecutionID id,
        StockID stockId,
        WalletID walletId,
        Double profitPercentage,
        Long buyExecutedQuantity,
        BigDecimal buyExecutedPrice,
        BigDecimal buyExecutedVolume,
        Long sellExecutedQuantity,
        BigDecimal sellExecutedPrice,
        BigDecimal sellExecutedVolume,
        ExecutionStatus status,
        Instant boughtAt,
        Instant soldAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static UpdateExecutionCommandOutput from(final Execution anExecution) {
        return new UpdateExecutionCommandOutput(
                anExecution.getId(),
                anExecution.getStockId(),
                anExecution.getWalletId(),
                anExecution.getProfitPercentage(),
                anExecution.getBuyExecutedQuantity(),
                anExecution.getBuyExecutedPrice(),
                anExecution.getBuyExecutedVolume(),
                anExecution.getSellExecutedQuantity(),
                anExecution.getSellExecutedPrice(),
                anExecution.getSellExecutedVolume(),
                anExecution.getStatus(),
                anExecution.getBoughtAt(),
                anExecution.getSoldAt(),
                anExecution.getCreatedAt(),
                anExecution.getUpdatedAt()
        );
    }
}

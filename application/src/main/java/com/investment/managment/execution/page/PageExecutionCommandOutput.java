package com.investment.managment.execution.page;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.time.Instant;

public record PageExecutionCommandOutput(
        ExecutionID id,
        ExecutionID origin,
        StockID stockId,
        WalletID walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status,
        BigDecimal pnlOpen,
        BigDecimal pnlOpenPercentage,
        BigDecimal pnlClose,
        BigDecimal pnlClosePercentage,
        Instant executedAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static PageExecutionCommandOutput from(final Execution anExecution) {
        return new PageExecutionCommandOutput(
                anExecution.getId(),
                anExecution.getOrigin(),
                anExecution.getStockId(),
                anExecution.getWalletId(),
                anExecution.getProfitPercentage(),
                anExecution.getExecutedQuantity(),
                anExecution.getExecutedPrice(),
                anExecution.getExecutedVolume(),
                anExecution.getStatus(),
                anExecution.getPnlOpen(),
                anExecution.getPnlOpenPercentage(),
                anExecution.getPnlClose(),
                anExecution.getPnlClosePercentage(),
                anExecution.getExecutedAt(),
                anExecution.getCreatedAt(),
                anExecution.getUpdatedAt()
        );
    }
}

package com.investment.managment.execution.summarybystock;

import com.investment.managment.execution.summary.ExecutionSummaryByStock;

public record SummaryExecutionCommandOutput(
        String stockId,
        String symbol,
        Long totalQuantity,
        Long totalSoldQuantity,
        Long totalCustodyQuantity
) {
    public static SummaryExecutionCommandOutput from(final ExecutionSummaryByStock executionSummaryByStock) {
        return new SummaryExecutionCommandOutput(
                executionSummaryByStock.stockId(),
                executionSummaryByStock.symbol(),
                executionSummaryByStock.totalQuantity(),
                executionSummaryByStock.totalSoldQuantity(),
                executionSummaryByStock.totalCustodyQuantity()
        );
    }
}

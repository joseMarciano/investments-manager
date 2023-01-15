package com.investment.managment.execution.summarybystock;

import com.investment.managment.execution.summary.ExecutionSummaryByStock;

public record ListExecutionCommandOutput(
        String symbol
) {
    public static ListExecutionCommandOutput from(final ExecutionSummaryByStock executionSummaryByStock) {
        return new ListExecutionCommandOutput(
                executionSummaryByStock.symbol()
        );
    }
}

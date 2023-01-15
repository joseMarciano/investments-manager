package com.investment.managment.execution.summarybystock;

import com.investment.managment.execution.summary.ExecutionSummaryByStock;

public record SummaryExecutionCommandOutput(
        String symbol
) {
    public static SummaryExecutionCommandOutput from(final ExecutionSummaryByStock executionSummaryByStock) {
        return new SummaryExecutionCommandOutput(
                executionSummaryByStock.symbol()
        );
    }
}

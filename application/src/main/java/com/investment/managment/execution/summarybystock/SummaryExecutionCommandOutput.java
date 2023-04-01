package com.investment.managment.execution.summarybystock;

import java.math.BigDecimal;

public record SummaryExecutionCommandOutput(
        String stockId,
        String symbol,
        Long totalQuantity,
        Long totalSoldQuantity,
        Long totalCustodyQuantity,
        BigDecimal totalPnlClose,
        BigDecimal totalPnlOpen
) {

    public static SummaryExecutionCommandOutput with(final String stockId,
                                                     final String symbol,
                                                     final Long totalQuantity,
                                                     final Long totalSoldQuantity,
                                                     final Long totalCustodyQuantity,
                                                     final BigDecimal totalPnlClose,
                                                     final BigDecimal totalPnlOpen) {
        return new SummaryExecutionCommandOutput(
                stockId,
                symbol,
                totalQuantity,
                totalSoldQuantity,
                totalCustodyQuantity,
                totalPnlClose,
                totalPnlOpen
        );
    }
}

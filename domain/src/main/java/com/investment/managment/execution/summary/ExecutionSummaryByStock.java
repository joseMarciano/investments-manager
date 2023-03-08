package com.investment.managment.execution.summary;


public class ExecutionSummaryByStock {
    private final String stockId;
    private final String symbol;
    private final Long totalQuantity;
    private final Long totalSoldQuantity;
    private final Long totalCustodyQuantity;

    public ExecutionSummaryByStock(final String stockId, final String symbol, final Long totalQuantity, final Long totalSoldQuantity) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.totalQuantity = totalQuantity;
        this.totalSoldQuantity = totalSoldQuantity;
        this.totalCustodyQuantity = this.totalQuantity - this.totalSoldQuantity;
    }


    public String stockId() {
        return stockId;
    }

    public String symbol() {
        return symbol;
    }

    public Long totalQuantity() {
        return totalQuantity;
    }

    public Long totalSoldQuantity() {
        return totalSoldQuantity;
    }

    public Long totalCustodyQuantity() {
        return totalCustodyQuantity;
    }
}
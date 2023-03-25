package com.investment.managment.execution;

import com.investment.managment.builder.AbstractBuilder;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.handler.ThrowableHandler;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.time.Instant;

public final class ExecutionBuilder extends AbstractBuilder<Execution> {

    @Override
    protected void validate() {
        ExecutionValidator.from(this.target, ThrowableHandler.newHandler()).validate();
    }

    @Override
    protected void afterValidate() {
        this.target.calculateExecutedVolume();
    }

    private ExecutionBuilder(final Execution execution) {
        super(execution);
    }

    public static ExecutionBuilder create() {
        return new ExecutionBuilder(new Execution());
    }

    public static ExecutionBuilder from(final Execution aExecution) {
        return new ExecutionBuilder(aExecution);
    }

    public ExecutionBuilder stockId(final StockID stockId) {
        this.target.stockId = stockId;
        return this;
    }

    public ExecutionBuilder walletId(final WalletID walletId) {
        this.target.walletId = walletId;
        return this;
    }

    public ExecutionBuilder executedQuantity(final Long executedQuantity) {
        this.target.executedQuantity = executedQuantity;
        return this;
    }

    public ExecutionBuilder executedPrice(final BigDecimal executedPrice) {
        this.target.executedPrice = executedPrice;
        return this;
    }

    public ExecutionBuilder status(final ExecutionStatus status) {
        this.target.status = status;
        return this;
    }

    public ExecutionBuilder profitPercentage(final Double profitPercentage) {
        this.target.profitPercentage = profitPercentage;
        return this;
    }

    public ExecutionBuilder executedAt(final Instant executedAt) {
        this.target.executedAt = executedAt;
        return this;
    }

    public ExecutionBuilder pnlOpen(final BigDecimal pnlOpen) {
        this.target.pnlOpen = pnlOpen;
        return this;
    }

    public ExecutionBuilder origin(final ExecutionID origin) {
        this.target.origin = origin;
        return this;
    }

}

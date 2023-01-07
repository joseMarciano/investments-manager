package com.investment.managment.execution;

import com.investment.managment.builder.AbstractBuilder;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.handler.ThrowableHandler;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;

public final class ExecutionBuilder extends AbstractBuilder<Execution> {

    @Override
    protected void validate() {
        ExecutionValidator.from(this.target, ThrowableHandler.newHandler()).validate();
    }

    @Override
    protected void afterValidate() {
        this.target.calculateBuyExecutedVolume();
        this.target.calculateSellExecutedVolume();
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

    public ExecutionBuilder buyExecutedQuantity(final Long buyExecutedQuantity) {
        this.target.buyExecutedQuantity = buyExecutedQuantity;
        return this;
    }

    public ExecutionBuilder sellExecutedQuantity(final Long sellExecutedQuantity) {
        this.target.sellExecutedQuantity = sellExecutedQuantity;
        return this;
    }

    public ExecutionBuilder buyExecutedPrice(final BigDecimal buyExecutedPrice) {
        this.target.buyExecutedPrice = buyExecutedPrice;
        return this;
    }

    public ExecutionBuilder sellExecutedPrice(final BigDecimal sellExecutedPrice) {
        this.target.sellExecutedPrice = sellExecutedPrice;
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

    public ExecutionBuilder origin(final ExecutionID origin) {
        this.target.origin = origin;
        return this;
    }

}

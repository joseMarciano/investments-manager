package com.investment.managment.stock;

import com.investment.managment.builder.AbstractBuilder;
import com.investment.managment.validation.handler.ThrowableHandler;

public final class StockBuilder extends AbstractBuilder<Stock> {

    @Override
    protected void validate() {
        StockValidator.from(this.target, ThrowableHandler.newHandler()).validate();
    }

    private StockBuilder(final Stock stock) {
        super(stock);
    }

    public static StockBuilder create() {
        return new StockBuilder(new Stock());
    }

    public static StockBuilder from(final Stock aStock) {
        return new StockBuilder(aStock);
    }

    public StockBuilder symbol(final String aSymbol) {
        this.target.symbol = aSymbol;
        return this;
    }
}

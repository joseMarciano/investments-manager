package com.investment.managment.stock.create;

public record CreateStockCommandInput(
        String symbol
) {

    public static CreateStockCommandInput with(final String symbol) {
        return new CreateStockCommandInput(symbol);
    }
}

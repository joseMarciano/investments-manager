package com.investment.managment.stock.update;

import com.investment.managment.stock.StockID;

import java.time.Instant;

public record UpdateStockCommandInput(
        StockID id,
        String symbol
) {

    public static UpdateStockCommandInput with(final String id, final String name) {
        return new UpdateStockCommandInput(StockID.from(id), name);
    }
}

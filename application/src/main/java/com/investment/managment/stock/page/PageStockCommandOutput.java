package com.investment.managment.stock.page;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;

public record PageStockCommandOutput(
        StockID id,
        String symbol
) {
    public static PageStockCommandOutput from(final Stock aStock) {
        return new PageStockCommandOutput(
                aStock.getId(),
                aStock.getSymbol()
        );
    }
}

package com.investment.managment.stock.update;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;

import java.time.Instant;

public record UpdateStockCommandOutput(
        StockID id,
        String symbol,
        Instant createdAt,
        Instant updatedAt
) {
    public static UpdateStockCommandOutput from(final Stock aStock) {
        return new UpdateStockCommandOutput(
                aStock.getId(),
                aStock.getSymbol(),
                aStock.getCreatedAt(),
                aStock.getUpdatedAt()
        );
    }
}

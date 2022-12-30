package com.investment.managment.stock.create;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;

import java.time.Instant;

public record CreateStockCommandOutput(
        StockID id,
        String symbol,
        Instant createdAt,
        Instant updatedAt
) {
    public static CreateStockCommandOutput from(final Stock aStock) {
        return new CreateStockCommandOutput(
                aStock.getId(),
                aStock.getSymbol(),
                aStock.getCreatedAt(),
                aStock.getUpdatedAt()
        );
    }
}

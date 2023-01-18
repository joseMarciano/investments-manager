package com.investment.managment.execution.page;

import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

public record ExecutionSearchQuery(
        SearchQuery searchQuery,
        WalletID walletID,
        StockID stockID
) {

    public static ExecutionSearchQuery of(final SearchQuery searchQuery, final WalletID walletID, final StockID stockID) {
        return new ExecutionSearchQuery(
                searchQuery,
                walletID,
                stockID
        );
    }


}

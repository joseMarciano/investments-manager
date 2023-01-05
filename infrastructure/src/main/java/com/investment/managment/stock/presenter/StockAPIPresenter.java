package com.investment.managment.stock.presenter;

import com.investment.managment.http.feing.marketdata.GetAllTickersResponse;
import com.investment.managment.stock.models.PageStockResponse;
import com.investment.managment.stock.page.PageStockCommandOutput;
import com.investment.managment.stock.searcher.retrieve.StockRetrieveAllResponse;

public interface StockAPIPresenter {

    static PageStockResponse present(final PageStockCommandOutput command) {
        return new PageStockResponse(
                command.id().getValue(),
                command.symbol()
        );
    }

    static StockRetrieveAllResponse present(final GetAllTickersResponse response) {
        return new StockRetrieveAllResponse(
                response.name(),
                response.ticker()
        );
    }
}

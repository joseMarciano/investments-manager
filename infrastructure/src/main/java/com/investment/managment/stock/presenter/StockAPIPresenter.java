package com.investment.managment.stock.presenter;

import com.investment.managment.stock.models.PageStockResponse;
import com.investment.managment.stock.page.PageStockCommandOutput;

public interface StockAPIPresenter {

    static PageStockResponse present(PageStockCommandOutput command) {
        return new PageStockResponse(
                command.id().getValue(),
                command.symbol()
        );
    }
}

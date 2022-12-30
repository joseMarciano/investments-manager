package com.investment.managment.stock.page;

import com.investment.managment.UseCase;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.StockGateway;

public class PageStockUseCase extends UseCase<SearchQuery, Pagination<PageStockCommandOutput>> {

    private final StockGateway stockGateway;

    public PageStockUseCase(final StockGateway stockGateway) {
        this.stockGateway = stockGateway;
    }

    @Override
    public Pagination<PageStockCommandOutput> execute(final SearchQuery searchQuery) {
        return stockGateway.findAll(searchQuery)
                .map(PageStockCommandOutput::from);
    }
}

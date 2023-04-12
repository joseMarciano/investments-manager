package com.investment.managment.api.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.models.PageStockResponse;
import com.investment.managment.stock.page.PageStockUseCase;
import com.investment.managment.stock.presenter.StockAPIPresenter;
import com.investment.managment.stock.searcher.StockSearcher;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController implements StockAPI {

    private final PageStockUseCase pageStockUseCase;

    private final StockSearcher stockSearcher;
    private final StockAPIScheduleTasks stockAPIScheduleTasks;


    public StockController(final PageStockUseCase pageStockUseCase,
                           final StockSearcher stockSearcher,
                           final StockAPIScheduleTasks stockAPIScheduleTasks) {
        this.pageStockUseCase = pageStockUseCase;
        this.stockSearcher = stockSearcher;
        this.stockAPIScheduleTasks = stockAPIScheduleTasks;
    }

    @Override
    public Pagination<PageStockResponse> page(final int limit,
                                              final int offset,
                                              final String filter,
                                              final String sort,
                                              final String direction) {

        final var searchQuery = new SearchQuery(offset,
                limit,
                sort,
                direction,
                filter);

        return pageStockUseCase.execute(searchQuery).map(StockAPIPresenter::present);
    }

    @Override
    public void updateOrCreate() {
        this.stockAPIScheduleTasks.updateOrCreateStocks();
    }
}

package com.investment.managment.api.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.models.PageStockResponse;
import com.investment.managment.stock.page.PageStockUseCase;
import com.investment.managment.stock.presenter.StockAPIPresenter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController implements StockAPI {

    private final PageStockUseCase pageStockUseCase;

    public StockController(final PageStockUseCase pageStockUseCase) {
        this.pageStockUseCase = pageStockUseCase;
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
}

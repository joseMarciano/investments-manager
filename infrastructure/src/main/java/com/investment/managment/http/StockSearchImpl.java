package com.investment.managment.http;

import com.investment.managment.http.feing.StockHttpSearchClient;
import com.investment.managment.stock.presenter.StockAPIPresenter;
import com.investment.managment.stock.searcher.StockSearcher;
import com.investment.managment.stock.searcher.retrieve.StockRetrieveAllResponse;
import com.investment.managment.util.CollectionUtil;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StockSearchImpl implements StockSearcher {

    private final StockHttpSearchClient stockHttpSearchClient;

    public StockSearchImpl(final StockHttpSearchClient stockHttpSearchClient) {
        this.stockHttpSearchClient = stockHttpSearchClient;
    }

    @Override
    public List<StockRetrieveAllResponse> getAllTickers() {
        return CollectionUtil.mapTo(this.stockHttpSearchClient.getAllTickers(), StockAPIPresenter::present);
    }
}

package com.investment.managment.stock.searcher.retrieve;

import java.util.List;

public interface StockRetrieveAllSearcher {
    List<StockRetrieveAllResponse> getAllTickers();
}

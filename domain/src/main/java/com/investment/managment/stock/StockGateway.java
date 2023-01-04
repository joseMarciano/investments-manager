package com.investment.managment.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;

import java.util.Optional;

public interface StockGateway {
    Stock create(Stock aStock);

    Stock update(Stock aStock);

    Optional<Stock> findById(StockID anId);

    Optional<Stock> findBySymbol(String symbol);

    Pagination<Stock> findAll(SearchQuery query);

    void deleteById(StockID id);
}

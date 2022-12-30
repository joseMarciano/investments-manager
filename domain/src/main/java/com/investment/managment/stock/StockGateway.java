package com.investment.managment.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;

import java.util.Optional;

public interface StockGateway {
    Stock create(Stock aStock);

    Stock update(Stock aStock);

    Optional<Stock> findById(StockID anId);

    Pagination<Stock> findAll(SearchQuery query);

    void deleteById(StockID id);
}

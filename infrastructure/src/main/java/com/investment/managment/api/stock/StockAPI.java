package com.investment.managment.api.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.stock.models.PageStockResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/stocks")
public interface StockAPI {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Pagination<PageStockResponse> page(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "filter", required = false) String filter,
                                       @RequestParam(value = "sort", defaultValue = "symbol") String sort,
                                       @RequestParam(value = "direction", defaultValue = "asc") String direction);


}

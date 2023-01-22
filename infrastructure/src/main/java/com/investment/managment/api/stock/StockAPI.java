package com.investment.managment.api.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.stock.models.PageStockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/stocks")
public interface StockAPI {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Pagination of Stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
    })
    Pagination<PageStockResponse> page(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "filter", required = false) String filter,
                                       @RequestParam(value = "sort", defaultValue = "symbol") String sort,
                                       @RequestParam(value = "direction", defaultValue = "asc") String direction);


}

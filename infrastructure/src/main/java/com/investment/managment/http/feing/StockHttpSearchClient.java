package com.investment.managment.http.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign-client.market-data.name}", url = "${feign-client.market-data.url}")
public interface StockHttpSearchClient {
    @RequestMapping(method = GET, value = "/tickers")
    List<GetAllTickersResponseFeign> getAllTickers();
}

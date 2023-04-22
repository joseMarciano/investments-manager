package com.investment.managment.http.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign-client.investment-calculator.name}", url = "${feign-client.investment-calculator.url}")
public interface InvestmentCalculatorHttpSearch {
    @RequestMapping(method = GET, value = "/health")
    void check();
}

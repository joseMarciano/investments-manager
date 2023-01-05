package com.investment.managment.http.feing.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetAllTickersResponse(
        String name,
        String ticker
) {
}

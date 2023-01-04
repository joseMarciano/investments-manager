package com.investment.managment.http.feing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetAllTickersResponseFeign(
        String name,
        String ticker
) {
}

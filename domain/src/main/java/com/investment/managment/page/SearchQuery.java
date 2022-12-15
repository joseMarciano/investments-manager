package com.investment.managment.page;

public record SearchQuery(
        int offset,
        int limit,
        String sort,
        String direction,
        String filter
) {
}

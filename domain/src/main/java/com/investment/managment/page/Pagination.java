package com.investment.managment.page;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(
        int offset,
        int limit,
        long total,
        List<T> items
) {

    public <R> Pagination<R> map(Function<T, R> mapper) {
        return new Pagination<>(
                offset(),
                limit(),
                total(),
                items.stream().map(mapper).toList()
        );
    }

}

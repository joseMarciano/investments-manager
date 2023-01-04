package com.investment.managment.util;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static <IN, OUT> List<OUT> mapTo(List<IN> input, Function<IN, OUT> mapper) {
        return ofNullable(input)
                .map(list -> list.stream().map(mapper).toList())
                .orElse(Collections.emptyList());
    }
}

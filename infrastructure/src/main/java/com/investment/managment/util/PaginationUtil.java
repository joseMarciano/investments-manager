package com.investment.managment.util;

import com.investment.managment.page.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static Pageable buildPage(final SearchQuery searchQuery) {
        final int limit = searchQuery.limit();
        final int offset = searchQuery.offset();
        final var direction = Sort.Direction.fromOptionalString(searchQuery.direction())
                .orElse(Sort.Direction.ASC);
        final var sortProperty = searchQuery.sort();

        return PageRequest.of(offset, limit, direction, sortProperty);
    }
}

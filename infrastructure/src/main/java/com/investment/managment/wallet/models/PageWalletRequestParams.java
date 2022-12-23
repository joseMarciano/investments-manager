package com.investment.managment.wallet.models;

public record PageWalletRequestParams(
        int offset,
        int limit,
        String sort,
        String direction,
        String filter
) {
}

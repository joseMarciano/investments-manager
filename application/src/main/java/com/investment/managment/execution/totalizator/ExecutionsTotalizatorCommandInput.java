package com.investment.managment.execution.totalizator;

public record ExecutionsTotalizatorCommandInput(
        String walletId,
        String stockId

) {

    public static ExecutionsTotalizatorCommandInput with(final String walletId,
                                                         final String stockId) {
        return new ExecutionsTotalizatorCommandInput(walletId, stockId);
    }

}

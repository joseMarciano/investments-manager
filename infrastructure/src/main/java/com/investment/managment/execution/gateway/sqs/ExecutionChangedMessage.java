package com.investment.managment.execution.gateway.sqs;

import com.investment.managment.execution.Execution;

public record ExecutionChangedMessage(
        Execution execution,
        ExecutionChangeReason reason
) {

    public static ExecutionChangedMessage of(final Execution execution, final ExecutionChangeReason reason) {
        return new ExecutionChangedMessage(execution, reason);
    }
}

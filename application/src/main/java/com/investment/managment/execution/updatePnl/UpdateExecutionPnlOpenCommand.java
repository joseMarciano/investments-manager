package com.investment.managment.execution.updatePnl;

import com.investment.managment.execution.ExecutionID;

import java.math.BigDecimal;

public record UpdateExecutionPnlOpenCommand(
        ExecutionID id,
        BigDecimal pnlOpen,
        BigDecimal pnlOpenPercentage) {
    public static UpdateExecutionPnlOpenCommand with(final ExecutionID anID, final BigDecimal pnlOpen, final BigDecimal pnlOpenPercentage) {
        return new UpdateExecutionPnlOpenCommand(anID, pnlOpen, pnlOpenPercentage);
    }
}

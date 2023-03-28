package com.investment.managment.execution.totalizator;

import java.math.BigDecimal;

public record ExecutionsTotalizatorCommandOuput(
        Long totalSoldQuantity,
        BigDecimal totalPnlClose,
        Long totalBoughtQuantity,
        BigDecimal totalPnlOpen
) {

    public static ExecutionsTotalizatorCommandOuput with(final Long totalSoldQuantity,
                                                         final BigDecimal totalPnlClose,
                                                         final Long totalBoughtQuantity,
                                                         final BigDecimal totalPnlOpen) {
        return new ExecutionsTotalizatorCommandOuput(totalSoldQuantity, totalPnlClose, totalBoughtQuantity, totalPnlOpen);
    }
}

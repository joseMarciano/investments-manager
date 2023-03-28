package com.investment.managment.execution.totalizator;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.investment.managment.execution.ExecutionStatus.BUY;
import static com.investment.managment.execution.ExecutionStatus.SELL;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class ExecutionsTotalizatorUseCase extends UseCase<ExecutionsTotalizatorCommandInput, ExecutionsTotalizatorCommandOuput> {

    private final ExecutionGateway executionGateway;

    public ExecutionsTotalizatorUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public ExecutionsTotalizatorCommandOuput execute(final ExecutionsTotalizatorCommandInput aCommand) {
        final var stockId = StockID.from(aCommand.stockId());
        final var walletId = WalletID.from(aCommand.walletId());

        final var executionsMap = this.executionGateway.getExecutionsByStockIdAndWalletId(walletId, stockId)
                .stream().collect(Collectors.groupingBy(Execution::getStatus));

        final var totalSoldQuantity = getTotalQuantity(executionsMap, SELL);
        final var totalPnlClose = getTotalPnl(executionsMap, Execution::getPnlClose, SELL);
        final var totalBoughtQuantity = getTotalQuantity(executionsMap, BUY);
        final var totalPnlOpen = getTotalPnl(executionsMap, Execution::getPnlOpen, BUY);


        return ExecutionsTotalizatorCommandOuput.with(totalSoldQuantity,
                totalPnlClose,
                totalBoughtQuantity,
                totalPnlOpen
        );
    }

    public BigDecimal getTotalPnl(final Map<ExecutionStatus, List<Execution>> executionsMap, Function<Execution, BigDecimal> mapper, final ExecutionStatus status) {
        return ofNullable(executionsMap.get(status)).orElse(emptyList()).stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add)
                .orElse(ZERO);
    }

    public Long getTotalQuantity(final Map<ExecutionStatus, List<Execution>> executionsMap, final ExecutionStatus status) {
        return ofNullable(executionsMap.get(status)).orElse(emptyList())
                .stream()
                .map(Execution::getExecutedQuantity)
                .filter(Objects::nonNull)
                .reduce(Long::sum)
                .orElse(0L);
    }
}

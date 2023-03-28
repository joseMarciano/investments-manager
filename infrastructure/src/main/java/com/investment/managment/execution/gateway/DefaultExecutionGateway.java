package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.gateway.postgres.ExecutionPostgresGateway;
import com.investment.managment.execution.gateway.sqs.ExecutionSQSGateway;
import com.investment.managment.execution.page.ExecutionSearchQuery;
import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.page.Pagination;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Qualifier("default-execution-gateway")
public class DefaultExecutionGateway implements ExecutionGateway {

    private final ExecutionPostgresGateway executionPostgresGateway;
    private final ExecutionSQSGateway executionSQSGateway;

    public DefaultExecutionGateway(final ExecutionPostgresGateway executionPostgresGateway,
                                   final ExecutionSQSGateway executionSQSGateway
    ) {
        this.executionPostgresGateway = executionPostgresGateway;
        this.executionSQSGateway = executionSQSGateway;
    }

    @Override
    @Transactional
    public Execution create(final Execution anExecution) {
        final var execution = this.executionPostgresGateway.create(anExecution);
        this.executionSQSGateway.create(execution);
        return execution;
    }

    @Override
    @Transactional
    public void deleteById(final ExecutionID id) {
        this.executionPostgresGateway.deleteById(id);
        this.executionSQSGateway.deleteById(id);
    }

    @Override
    public boolean existsByOriginId(final ExecutionID... originId) {
        return this.executionPostgresGateway.existsByOriginId(originId);
    }

    @Override
    public List<Execution> findAllByOriginId(final ExecutionID originId) {
        return this.executionPostgresGateway.findAllByOriginId(originId);
    }

    @Override
    public Pagination<Execution> findAll(final ExecutionSearchQuery executionSearchQuery) {
        return this.executionPostgresGateway.findAll(executionSearchQuery);
    }

    @Override
    public Optional<Execution> findById(final ExecutionID anId) {
        return this.executionPostgresGateway.findById(anId);
    }

    @Override
    public List<ExecutionSummaryByStock> getExecutionSummaryByStock(final WalletID aWalletID) {
        return this.executionPostgresGateway.getExecutionSummaryByStock(aWalletID);
    }

    @Override
    @Transactional
    public Execution update(final Execution anExecution) {
        final var executionUpdated = this.executionPostgresGateway.update(anExecution);
        this.executionSQSGateway.update(executionUpdated);
        return executionUpdated;
    }

    @Override
    public List<Execution> getExecutionsByStockIdAndWalletId(final WalletID aWalletID, final StockID aStockID) {
        return this.executionPostgresGateway.getExecutionsByStockIdAndWalletId(aWalletID, aStockID);
    }
}

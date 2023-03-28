package com.investment.managment.config.execution;

import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.execution.findById.FindExecutionByIdUseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.page.PageExecutionUseCase;
import com.investment.managment.execution.sell.SellExecutionUseCase;
import com.investment.managment.execution.summarybystock.SummaryExecutionUseCase;
import com.investment.managment.execution.totalizator.ExecutionsTotalizatorUseCase;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.execution.update.buy.UpdateBuyExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellExecutionUseCase;
import com.investment.managment.execution.updatePnl.UpdateExecutionPnlOpenUseCase;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.wallet.WalletGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionUseCaseConfig {

    private final ExecutionGateway defaultExecutionGateway;
    private final ExecutionGateway executionPostgresGateway;
    private final StockGateway stockGateway;
    private final WalletGateway walletGateway;

    public ExecutionUseCaseConfig(final @Qualifier("default-execution-gateway") ExecutionGateway defaultExecutionGateway,
                                  final @Qualifier("postgres-execution-gateway") ExecutionGateway executionPostgresGateway,
                                  final StockGateway stockGateway,
                                  final WalletGateway walletGateway) {
        this.defaultExecutionGateway = defaultExecutionGateway;
        this.stockGateway = stockGateway;
        this.walletGateway = walletGateway;
        this.executionPostgresGateway = executionPostgresGateway;
    }

    @Bean
    public CreateExecutionUseCase createExecutionUseCase() {
        return new CreateExecutionUseCase(this.defaultExecutionGateway, this.stockGateway, this.walletGateway);
    }

    @Bean
    public UpdateBuyExecutionUseCase updateBuyFieldsExecutionUseCase() {
        return new UpdateBuyExecutionUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public UpdateSellExecutionUseCase updateSellFieldsExecutionUseCase() {
        return new UpdateSellExecutionUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public DeleteExecutionByIdUseCase deleteExecutionByIdUseCase() {
        return new DeleteExecutionByIdUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public UpdateExecutionUseCase updateExecutionUseCase(final UpdateBuyExecutionUseCase updateBuyExecutionUseCase,
                                                         final UpdateSellExecutionUseCase updateSellExecutionUseCase) {
        return new UpdateExecutionUseCase(this.defaultExecutionGateway, updateBuyExecutionUseCase, updateSellExecutionUseCase);
    }

    @Bean
    public FindExecutionByIdUseCase findExecutionByIdUseCase() {
        return new FindExecutionByIdUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public SellExecutionUseCase sellExecutionUseCase() {
        return new SellExecutionUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public SummaryExecutionUseCase summaryExecutionUseCase() {
        return new SummaryExecutionUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public UpdateExecutionPnlOpenUseCase updateExecutionPnlOpenUseCase() {
        return new UpdateExecutionPnlOpenUseCase(this.executionPostgresGateway);
    }

    @Bean
    public PageExecutionUseCase pageExecutionUseCase() {
        return new PageExecutionUseCase(this.defaultExecutionGateway);
    }

    @Bean
    public ExecutionsTotalizatorUseCase executionsTotalizatorUseCase() {
        return new ExecutionsTotalizatorUseCase(this.executionPostgresGateway);
    }
}

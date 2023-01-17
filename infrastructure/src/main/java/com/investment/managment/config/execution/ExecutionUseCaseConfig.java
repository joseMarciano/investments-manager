package com.investment.managment.config.execution;

import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.execution.findById.FindExecutionByIdUseCase;
import com.investment.managment.execution.sell.SellExecutionUseCase;
import com.investment.managment.execution.summarybystock.SummaryExecutionUseCase;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.execution.update.buy.UpdateBuyExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellExecutionUseCase;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.wallet.WalletGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionUseCaseConfig {

    private final ExecutionGateway executionGateway;
    private final StockGateway stockGateway;
    private final WalletGateway walletGateway;

    public ExecutionUseCaseConfig(final ExecutionGateway executionGateway,
                                  final StockGateway stockGateway,
                                  final WalletGateway walletGateway) {
        this.executionGateway = executionGateway;
        this.stockGateway = stockGateway;
        this.walletGateway = walletGateway;
    }

    @Bean
    public CreateExecutionUseCase createExecutionUseCase() {
        return new CreateExecutionUseCase(this.executionGateway, this.stockGateway, this.walletGateway);
    }

    @Bean
    public UpdateBuyExecutionUseCase updateBuyFieldsExecutionUseCase() {
        return new UpdateBuyExecutionUseCase(this.executionGateway);
    }

    @Bean
    public UpdateSellExecutionUseCase updateSellFieldsExecutionUseCase() {
        return new UpdateSellExecutionUseCase(this.executionGateway);
    }

    @Bean
    public DeleteExecutionByIdUseCase deleteExecutionByIdUseCase() {
        return new DeleteExecutionByIdUseCase(this.executionGateway);
    }

    @Bean
    public UpdateExecutionUseCase updateExecutionUseCase(final UpdateBuyExecutionUseCase updateBuyExecutionUseCase,
                                                         final UpdateSellExecutionUseCase updateSellExecutionUseCase) {
        return new UpdateExecutionUseCase(this.executionGateway, updateBuyExecutionUseCase, updateSellExecutionUseCase);
    }

    @Bean
    public FindExecutionByIdUseCase findExecutionByIdUseCase() {
        return new FindExecutionByIdUseCase(this.executionGateway);
    }

    @Bean
    public SellExecutionUseCase sellExecutionUseCase() {
        return new SellExecutionUseCase(this.executionGateway);
    }

    @Bean
    public SummaryExecutionUseCase summaryExecutionUseCase() {
        return new SummaryExecutionUseCase(this.executionGateway);
    }
}

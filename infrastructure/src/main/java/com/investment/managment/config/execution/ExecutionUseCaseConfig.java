package com.investment.managment.config.execution;

import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.findById.FindExecutionByIdUseCase;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.execution.update.buy.UpdateBuyFieldsExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellFieldsExecutionUseCase;
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
    public UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase() {
        return new UpdateBuyFieldsExecutionUseCase(this.executionGateway);
    }

    @Bean
    public UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase() {
        return new UpdateSellFieldsExecutionUseCase(this.executionGateway);
    }


    @Bean
    public UpdateExecutionUseCase updateExecutionUseCase(final UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase,
                                                         final UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase) {
        return new UpdateExecutionUseCase(this.executionGateway, updateBuyFieldsExecutionUseCase, updateSellFieldsExecutionUseCase);
    }

    @Bean
    public FindExecutionByIdUseCase findExecutionByIdUseCase() {
        return new FindExecutionByIdUseCase(this.executionGateway);
    }
}

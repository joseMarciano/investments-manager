package com.investment.managment.config.stock;

import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.page.PageStockUseCase;
import com.investment.managment.stock.update.UpdateStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockUseCaseConfig {
    @Bean
    public CreateStockUseCase createStockUseCase(final StockGateway stockGateway) {
        return new CreateStockUseCase(stockGateway);
    }

    @Bean
    public UpdateStockUseCase updateStockUseCase(final StockGateway stockGateway) {
        return new UpdateStockUseCase(stockGateway);
    }

    @Bean
    public PageStockUseCase pageStockUseCase(final StockGateway stockGateway) {
        return new PageStockUseCase(stockGateway);
    }
}

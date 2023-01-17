package com.investment.managment.execution.persistence;

import com.investment.managment.DataBaseExtension;
import com.investment.managment.IntegrationTest;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.stock.persistence.StockRepository;
import com.investment.managment.util.InstantUtil;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.persistence.WalletJpaEntity;
import com.investment.managment.wallet.persistence.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@IntegrationTest
public class ExecutionPostgresGatewayIT extends DataBaseExtension {

    @Autowired
    private ExecutionGateway executionGateway;

    @Autowired
    private ExecutionRepository executionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void injectTest() {
        Assertions.assertNotNull(executionGateway);
        Assertions.assertNotNull(executionRepository);
    }

    @Test
    public void givenExecutions_whenCallsExeuctionSummaryByStock_shouldGroupCorrectly() {
        Assertions.assertEquals(0, executionRepository.count());
        Assertions.assertEquals(0, stockRepository.count());
        Assertions.assertEquals(0, walletRepository.count());

        persistExecution(ExecutionBuilder.create()
                .stockId(persistStock("MGLU3").getId())
                .walletId(persistWallet("Long Term").getId())
                .executedQuantity(45L)
                .executedPrice(BigDecimal.TEN)
                .status(ExecutionStatus.BUY)
                .executedAt(InstantUtil.now())
                .profitPercentage(8.04)
                .build());

        final var expectedWallet = persistWallet("Swing Trade");
        final var expectedStock = persistStock("PETR4");
        persistExecutions(expectedWallet, expectedStock);
        Assertions.assertEquals(2, stockRepository.count());
        Assertions.assertEquals(2, walletRepository.count());
        Assertions.assertEquals(4, executionRepository.count());

        final var executionSummaryByStockList =
                this.executionGateway.getExecutionSummaryByStock(expectedWallet.getId());

        executionSummaryByStockList.forEach(executionSummaryByStock -> {
            Assertions.assertEquals(executionSummaryByStock.symbol(), expectedStock.getSymbol());
        });
    }

    private void persistExecutions(final Wallet expectedWallet, final Stock expectedStock) {
        persistExecution(ExecutionBuilder.create()
                .stockId(expectedStock.getId())
                .walletId(expectedWallet.getId())
                .executedQuantity(45L)
                .executedPrice(BigDecimal.TEN)
                .status(ExecutionStatus.BUY)
                .executedAt(InstantUtil.now())
                .profitPercentage(8.04)
                .build());
        persistExecution(ExecutionBuilder.create()
                .stockId(expectedStock.getId())
                .walletId(expectedWallet.getId())
                .executedQuantity(45L)
                .executedPrice(BigDecimal.TEN)
                .status(ExecutionStatus.BUY)
                .executedAt(InstantUtil.now())
                .profitPercentage(8.04)
                .build());
        persistExecution(ExecutionBuilder.create()
                .stockId(expectedStock.getId())
                .walletId(expectedWallet.getId())
                .executedQuantity(45L)
                .executedPrice(BigDecimal.TEN)
                .status(ExecutionStatus.BUY)
                .executedAt(InstantUtil.now())
                .profitPercentage(8.04)
                .build());
    }

    private Wallet persistWallet(final String name) {
        return this.walletRepository.saveAndFlush(WalletJpaEntity.from(WalletBuilder.create().name(name).build()))
                .toAggregate();
    }

    private Stock persistStock(final String symbol) {
        return this.stockRepository.saveAndFlush(StockJpaEntity.from(StockBuilder.create().symbol(symbol).build()))
                .toAggregate();
    }

    public Execution persistExecution(final Execution execution) {
        return this.executionRepository.saveAndFlush(ExecutionJpaEntity.from(execution)).toAggregate();
    }


}

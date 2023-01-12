package com.investment.managment.execution.update;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.update.buy.UpdateBuyFieldsExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellFieldsExecutionUseCase;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@UseCaseTest
public class UpdateExecutionUseCaseTest {

    @InjectMocks
    private UpdateExecutionUseCase useCase;
    @Mock
    private ExecutionGateway executionGateway;
    @Mock
    private UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase;
    @Mock
    private UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase;


    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithBUYExecutionStatus_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 2L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedBuyExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final var expectedBoughtAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .buyExecutedQuantity(6L)
                        .buyExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .boughtAt(Instant.now())
                        .build();

        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBuyExecutedVolume,
                null,
                null,
                null,
                expectedStatus,
                expectedBoughtAt,
                null,
                Instant.now(),
                Instant.now()
        );

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBoughtAt
        );

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateBuyFieldsExecutionUseCase.execute(aCommand, anExecution))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.buyExecutedQuantity(), expectedBuyExecutedQuantity);
        Assertions.assertEquals(actualOutput.buyExecutedPrice(), expectedBuyExecutedPrice);
        Assertions.assertEquals(actualOutput.buyExecutedVolume(), expectedBuyExecutedVolume);
        Assertions.assertNull(actualOutput.sellExecutedQuantity());
        Assertions.assertNull(actualOutput.sellExecutedPrice());
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.boughtAt(), expectedBoughtAt);
        Assertions.assertNull(actualOutput.soldAt());
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
        verify(updateBuyFieldsExecutionUseCase).execute(aCommand, anExecution);
        verify(updateSellFieldsExecutionUseCase, times(0)).execute(any(), any());
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithSELLExecutionStatus_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedSellExecutedQuantity = 2L;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedSellExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(6L)
                        .sellExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .soldAt(Instant.now())
                        .build();
        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                null,
                null,
                null,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSellExecutedVolume,
                expectedStatus,
                null,
                expectedSoldAt,
                Instant.now(),
                Instant.now()
        );


        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateSellFieldsExecutionUseCase.execute(aCommand, anExecution))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.sellExecutedQuantity(), expectedSellExecutedQuantity);
        Assertions.assertEquals(actualOutput.sellExecutedPrice(), expectedSellExecutedPrice);
        Assertions.assertEquals(actualOutput.sellExecutedVolume(), expectedSellExecutedVolume);
        Assertions.assertNull(actualOutput.buyExecutedQuantity());
        Assertions.assertNull(actualOutput.buyExecutedPrice());
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.soldAt(), expectedSoldAt);
        Assertions.assertNull(actualOutput.boughtAt());
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
        verify(updateSellFieldsExecutionUseCase).execute(aCommand, anExecution);
        verify(updateBuyFieldsExecutionUseCase, times(0)).execute(any(), any());
    }
}

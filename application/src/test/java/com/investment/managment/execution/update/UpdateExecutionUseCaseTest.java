package com.investment.managment.execution.update;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.update.buy.UpdateBuyExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellExecutionUseCase;
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
    private UpdateBuyExecutionUseCase updateBuyExecutionUseCase;
    @Mock
    private UpdateSellExecutionUseCase updateSellExecutionUseCase;


    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithBUYExecutionStatus_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOriginID = null;
        final var expectedExecutedAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .origin(expectedOriginID)
                        .walletId(expectedWalletId)
                        .executedQuantity(6L)
                        .executedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build();

        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedOriginID,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedVolume,
                expectedStatus,
                expectedExecutedAt,
                Instant.now(),
                Instant.now()
        );

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateBuyExecutionUseCase.execute(aCommand, anExecution))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.originId(), expectedOriginID);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.executedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(actualOutput.executedPrice(), expectedExecutedPrice);
        Assertions.assertEquals(actualOutput.executedVolume(), expectedExecutedVolume);
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.executedAt(), expectedExecutedAt);
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
        verify(updateBuyExecutionUseCase).execute(aCommand, anExecution);
        verify(updateSellExecutionUseCase, times(0)).execute(any(), any());
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithSELLExecutionStatus_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedOriginID = ExecutionID.unique();
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .origin(expectedOriginID)
                        .executedQuantity(6L)
                        .executedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build();
        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedOriginID,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedVolume,
                expectedStatus,
                expectedSoldAt,
                Instant.now(),
                Instant.now()
        );


        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedSoldAt
        );

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateSellExecutionUseCase.execute(aCommand, anExecution))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.originId(), expectedOriginID);
        Assertions.assertEquals(actualOutput.executedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(actualOutput.executedPrice(), expectedExecutedPrice);
        Assertions.assertEquals(actualOutput.executedVolume(), expectedExecutedVolume);
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
        verify(updateSellExecutionUseCase).execute(aCommand, anExecution);
        verify(updateBuyExecutionUseCase, times(0)).execute(any(), any());
    }
}

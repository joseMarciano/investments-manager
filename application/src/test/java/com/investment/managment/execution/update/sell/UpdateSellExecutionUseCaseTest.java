package com.investment.managment.execution.update.sell;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class UpdateSellExecutionUseCaseTest {

    @InjectMocks
    private UpdateSellExecutionUseCase useCase;
    @Mock
    private ExecutionGateway executionGateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCase_shouldUpdateIt() {
        final var expectedOriginId = ExecutionID.unique();
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final var expectedExecutedAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .executedQuantity(6L)
                        .executedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build();

        final var expectedId = anExecution.getId();

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(executionGateway.findById(expectedOriginId))
                .thenReturn(Optional.of(ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .executedQuantity(10L)
                        .executedPrice(BigDecimal.ONE)
                        .status(ExecutionStatus.BUY)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build()));
        when(executionGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand, anExecution);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
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
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithInvalidExecutedQuantity_shouldReturnDomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedOriginId = ExecutionID.unique();
        final var expectedExecutedQuantity = 17L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final var expectedExecutedAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .executedQuantity(2L)
                        .executedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build();


        final var expectedId = anExecution.getId();
        final var expectedErrorMessage = "'executedPrice' should not be greater than 3";

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(executionGateway.findById(expectedOriginId))
                .thenReturn(Optional.of(ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .executedQuantity(10L)
                        .executedPrice(BigDecimal.ONE)
                        .status(ExecutionStatus.BUY)
                        .profitPercentage(8.00)
                        .executedAt(Instant.now())
                        .build()));

        when(executionGateway.findAllByOriginId(expectedOriginId))
                .thenReturn(List.of(ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .executedQuantity(7L)
                        .executedPrice(BigDecimal.TEN)
                        .status(expectedStatus)
                        .profitPercentage(7.00)
                        .executedAt(Instant.now())
                        .build(), anExecution));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand, anExecution));


        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());


    }
}

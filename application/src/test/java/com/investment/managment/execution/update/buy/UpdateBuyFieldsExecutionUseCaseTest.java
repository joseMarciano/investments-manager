package com.investment.managment.execution.update.buy;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class UpdateBuyFieldsExecutionUseCaseTest {

    @InjectMocks
    private UpdateBuyFieldsExecutionUseCase useCase;
    @Mock
    private ExecutionGateway executionGateway;

//    1 -> verificar se existe execuções com originID, se existir validar o quantity;

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithoutExecutionsSold_shouldUpdateIt() {
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

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBoughtAt
        );


        when(executionGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand, anExecution);


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
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithExecutionsSoldAndInvalidExecutedQuantity_shouldReturnDomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 2L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
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
        final var expectedErrorMessage = "'buyExecutedPrice' can not be less than 4";

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBoughtAt
        );


        when(executionGateway.existsByOriginId(expectedId))
                .thenReturn(true);
        when(executionGateway.findAllByOriginId(expectedId))
                .thenReturn(List.of(ExecutionBuilder
                        .create()
                        .origin(expectedId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(4L)
                        .sellExecutedPrice(BigDecimal.ONE)
                        .status(ExecutionStatus.SELL)
                        .profitPercentage(8.00)
                        .soldAt(Instant.now())
                        .build()));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand, anExecution));


        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());


    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithExecutionsSoldAndInvalidExecutedQuantity_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 7L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedBuyExecutedVolume = BigDecimal.valueOf(70);
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
        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBoughtAt
        );


        when(executionGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(executionGateway.existsByOriginId(expectedId))
                .thenReturn(false);

        final var actualOutput = useCase.execute(aCommand, anExecution);


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


    }
}

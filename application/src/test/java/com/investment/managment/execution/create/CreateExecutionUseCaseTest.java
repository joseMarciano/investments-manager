package com.investment.managment.execution.create;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

@UseCaseTest
public class CreateExecutionUseCaseTest {

    @InjectMocks
    private CreateExecutionUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Mock
    private StockGateway stockGateway;

    @Mock
    private ExecutionGateway executionGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateUseCase_shouldInstantiateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final var expectedExecutedAt = Instant.now();


        final var aCommand = new CreateExecutionCommandInput(
                expectedStockId.getValue(),
                expectedWalletId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(stockGateway.findById(expectedStockId))
                .thenReturn(Optional.of(mock(Stock.class)));

        when(walletGateway.findById(expectedWalletId))
                .thenReturn(Optional.of(mock(Wallet.class)));

        when(executionGateway.create(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput.id());
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
        Assertions.assertEquals(actualOutput.updatedAt(), actualOutput.createdAt());
    }

    @Test
    public void givenAInvalidCommandWithNullStockId_whenCallsCreateUseCase_shouldReturnNotFoundException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 5.00;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Stock", expectedStockId.getValue());


        final var aCommand = new CreateExecutionCommandInput(
                expectedStockId.getValue(),
                expectedWalletId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(stockGateway.findById(any()))
                .thenReturn(Optional.empty());


        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidCommandWithNullWalletId_whenCallsCreateUseCase_shouldReturnNotFoundException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 2L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 5.00;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Wallet", expectedWalletId.getValue());


        final var aCommand = new CreateExecutionCommandInput(
                expectedStockId.getValue(),
                expectedWalletId.getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(stockGateway.findById(expectedStockId))
                .thenReturn(Optional.of(mock(Stock.class)));

        when(walletGateway.findById(expectedWalletId))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

}

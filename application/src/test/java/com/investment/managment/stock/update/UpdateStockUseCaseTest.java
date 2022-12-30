package com.investment.managment.stock.update;

import com.investment.managment.UseCaseTest;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class UpdateStockUseCaseTest {

    @InjectMocks
    private UpdateStockUseCase useCase;

    @Mock
    private StockGateway stockGateway;

    @Test
    public void givenAValidStock_whenCallsUseCaseWithValidParams_shouldUpdateIt() {
        final var aStock = StockBuilder.create()
                .symbol("PRE")
                .build();
        final var expectedSymbol = "PETR3";
        final var expectedId = aStock.getId();


        final var aCommand = new UpdateStockCommandInput(expectedId, expectedSymbol);

        when(stockGateway.update(any(Stock.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        when(stockGateway.findById(any(StockID.class)))
                .thenReturn(Optional.of(aStock));

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.symbol(), expectedSymbol);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertTrue(actualOutput.createdAt().isBefore(actualOutput.updatedAt()));
    }

    @Test
    public void givenAValidStock_whenCallsUseCaseWithAInvalidNullName_shouldReturnADomainException() {
        final var aStock = StockBuilder.create()
                .symbol("PETR4")
                .build();
        final String expectedSymbol = null;
        final var expectedId = aStock.getId();
        final var expectedErrorMessage = "'symbol' should not be null";

        final var aCommand = new UpdateStockCommandInput(expectedId, expectedSymbol);

        when(stockGateway.findById(any(StockID.class)))
                .thenReturn(Optional.of(aStock));


        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidStock_whenCallsUseCaseWithAInvalidEmptyName_shouldReturnADomainException() {
        final var aStock = StockBuilder.create()
                .symbol("PER")
                .build();
        final var expectedSymbol = "  ";
        final var expectedId = aStock.getId();
        final var expectedErrorMessage = "'symbol' should not be empty";

        final var aCommand = new UpdateStockCommandInput(expectedId, expectedSymbol);

        when(stockGateway.findById(any(StockID.class)))
                .thenReturn(Optional.of(aStock));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidStock_whenCallsUseCaseWithAInvalidBigName_shouldReturnADomainException() {
        final var aStock = StockBuilder.create()
                .symbol("long")
                .build();
        final var expectedSymbol = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedId = aStock.getId();
        final var expectedErrorMessage = "'symbol' should be between 1 and 20 characters";


        final var aCommand = new UpdateStockCommandInput(expectedId, expectedSymbol);

        when(stockGateway.findById(any(StockID.class)))
                .thenReturn(Optional.of(aStock));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());

    }


    @Test
    public void givenAValidStock_whenCallsUseCaseWithAInvalidID_shouldReturnANotFoundException() {
        final var expectedSymbol = "PETR3";
        final var expectedId = StockID.unique();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Stock", expectedId.getValue());

        final var aCommand = new UpdateStockCommandInput(expectedId, expectedSymbol);

        when(stockGateway.findById(any(StockID.class)))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}

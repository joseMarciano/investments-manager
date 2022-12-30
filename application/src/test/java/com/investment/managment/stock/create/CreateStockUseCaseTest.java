package com.investment.managment.stock.create;

import com.investment.managment.UseCaseTest;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.create.CreateWalletCommandInput;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class CreateStockUseCaseTest {

    @InjectMocks
    private CreateStockUseCase useCase;

    @Mock
    private StockGateway stockGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateUseCase_shouldInstantiateIt() {
        final var expectedSymbol = "HGLG11";

        final var aCommand = new CreateStockCommandInput(expectedSymbol);

        when(stockGateway.create(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.symbol(), expectedSymbol);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
    }

    @Test
    public void givenAInvalidParamWithNullName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final String expectedSymbol = null;
        final var expectedErrorMessage = "'symbol' should not be null";

        final var aCommand = new CreateStockCommandInput(expectedSymbol);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final var expectedSymbol = "  ";
        final var expectedErrorMessage = "'symbol' should not be empty";

        final var aCommand = new CreateStockCommandInput(expectedSymbol);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final var expectedSymbol = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedErrorMessage = "'symbol' should be between 1 and 20 characters";

        final var aCommand = new CreateStockCommandInput(expectedSymbol);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}

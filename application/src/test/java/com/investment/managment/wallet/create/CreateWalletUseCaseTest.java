package com.investment.managment.wallet.create;

import com.investment.managment.UseCaseTest;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class CreateWalletUseCaseTest {

    @InjectMocks
    private CreateWalletUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateUseCase_shouldInstantiateIt() {
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var aCommand = new CreateWalletCommandInput(expectedName, expectedDescription, expectedColor);

        when(walletGateway.create(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.name(), expectedName);
        Assertions.assertEquals(actualOutput.description(), expectedDescription);
        Assertions.assertEquals(actualOutput.color(), expectedColor);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
    }

    @Test
    public void givenAInvalidParamWithNullName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final String expectedName = null;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = new CreateWalletCommandInput(expectedName, expectedDescription, expectedColor);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final var expectedName = "  ";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCommand = new CreateWalletCommandInput(expectedName, expectedDescription, expectedColor);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigName_whenCallsCreateUseCase_shouldReturnADomainException() {
        final var expectedName = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";

        final var aCommand = new CreateWalletCommandInput(expectedName, expectedDescription, expectedColor);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigDescription_whenCallsCreateUseCase_shouldReturnADomainException() {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";

        final var aCommand = new CreateWalletCommandInput(expectedName, expectedDescription, expectedColor);
        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}

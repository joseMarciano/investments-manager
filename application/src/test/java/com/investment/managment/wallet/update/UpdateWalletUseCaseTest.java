package com.investment.managment.wallet.update;

import com.investment.managment.UseCaseTest;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class UpdateWalletUseCaseTest {

    @InjectMocks
    private UpdateWalletUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithValidParams_shouldUpdateIt() {
        final var aWallet = WalletBuilder.create()
                .name("long")
                .description("a this is wrong")
                .color("FF3Df")
                .build();
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedId = aWallet.getId();


        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.update(any(Wallet.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.of(aWallet));

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.name(), expectedName);
        Assertions.assertEquals(actualOutput.description(), expectedDescription);
        Assertions.assertEquals(actualOutput.color(), expectedColor);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertTrue(actualOutput.createdAt().isBefore(actualOutput.updatedAt()));
    }

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithAInvalidNullName_shouldReturnADomainException() {
        final var aWallet = WalletBuilder.create()
                .name("long")
                .description("a this is wrong")
                .color("FF3Df")
                .build();
        final String expectedName = null;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedId = aWallet.getId();
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.of(aWallet));


        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithAInvalidEmptyName_shouldReturnADomainException() {
        final var aWallet = WalletBuilder.create()
                .name("long")
                .description("a this is wrong")
                .color("FF3Df")
                .build();
        final var expectedName = "  ";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedId = aWallet.getId();
        final var expectedErrorMessage = "'name' should not be empty";

        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.of(aWallet));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithAInvalidBigName_shouldReturnADomainException() {
        final var aWallet = WalletBuilder.create()
                .name("long")
                .description("a this is wrong")
                .color("FF3Df")
                .build();
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
        final var expectedId = aWallet.getId();
        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";


        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.of(aWallet));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());

    }

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithAInvalidBigDescription_shouldReturnADomainException() {
        final var aWallet = WalletBuilder.create()
                .name("long")
                .description("a this is wrong")
                .color("FF3Df")
                .build();
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
        final var expectedId = aWallet.getId();
        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";

        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.of(aWallet));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidWallet_whenCallsUseCaseWithAInvalidID_shouldReturnANotFoundException() {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = "Long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedId = WalletID.unique();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Wallet", expectedId.getValue());

        final var aCommand = new UpdateWalletCommandInput(expectedId, expectedName, expectedDescription, expectedColor);

        when(walletGateway.findById(any(WalletID.class)))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}

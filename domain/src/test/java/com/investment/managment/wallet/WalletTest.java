package com.investment.managment.wallet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalletTest {

    @Test
    public void givenAValidParams_whenCallsNewWallet_shouldInstantiateIt() {
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var actualWallet = WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getName(), expectedName);
        Assertions.assertEquals(actualWallet.getDescription(), expectedDescription);
        Assertions.assertEquals(actualWallet.getColor(), expectedColor);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
    }
}

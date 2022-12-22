package com.investment.managment.wallet.create;

public record CreateWalletCommandInput(
        String name,
        String description,
        String color
) {

    public static CreateWalletCommandInput with(final String name, final String description, final String color) {
        return new CreateWalletCommandInput(name, description, color);
    }
}

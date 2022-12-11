package com.investment.managment.wallet.create;

public record CreateWalletCommandInput(
        String name,
        String description,
        String color
) {
}

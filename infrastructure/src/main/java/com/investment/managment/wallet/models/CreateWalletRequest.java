package com.investment.managment.wallet.models;

public record CreateWalletRequest(
        String name,
        String description,
        String color
) {
}

package com.investment.managment.wallet.models;

public record UpdateWalletRequest(
        String id,
        String name,
        String description,
        String color
) {
}

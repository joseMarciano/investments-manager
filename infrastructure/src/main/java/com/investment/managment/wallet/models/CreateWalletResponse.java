package com.investment.managment.wallet.models;

import java.time.Instant;

public record CreateWalletResponse(
        String id,
        String name,
        String description,
        String color,
        Instant createdAt,
        Instant updatedAt
) {
}

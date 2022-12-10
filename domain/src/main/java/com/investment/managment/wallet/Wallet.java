package com.investment.managment.wallet;

import com.investment.managment.AggregateRoot;

import java.time.Instant;

public class Wallet extends AggregateRoot<WalletID> {

    private final WalletID id;

    protected String name;

    protected String description;

    protected String color;

    private final Instant createdAt;

    private Instant updatedAt;


    protected Wallet() {
        final Instant now = Instant.now();
        this.id = WalletID.unique();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @Override
    protected WalletID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}

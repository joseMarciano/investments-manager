package com.investment.managment.wallet;

import com.investment.managment.AggregateRoot;
import com.investment.managment.util.InstantUtil;

import java.time.Instant;

public class Wallet extends AggregateRoot<WalletID> {

    private final WalletID id;

    protected String name;

    protected String description;

    protected String color;

    private final Instant createdAt;

    private Instant updatedAt;


    protected Wallet() {
        final Instant now = InstantUtil.now();
        this.id = WalletID.unique();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Wallet(final WalletID anId, final String aName, final String aDescription, final String aColor, final Instant createdAt, final Instant updatedAt) {
        this.id = anId;
        this.name = aName;
        this.description = aDescription;
        this.color = aColor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Wallet with(final WalletID anId, final String aName, final String aDescription, final String aColor, final Instant createdAt, final Instant updatedAt) {
        return new Wallet(anId, aName, aDescription, aColor, createdAt, updatedAt);
    }

    public Wallet update(final String aName, final String aDescription, final String aColor) {
        this.name = aName;
        this.description = aDescription;
        this.color = aColor;
        this.updatedAt = InstantUtil.now();
        return WalletBuilder.from(this).build();
    }

    @Override
    public WalletID getId() {
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

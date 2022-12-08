package com.investment.managment.wallet;

import com.investment.managment.builder.AbstractBuilder;

public final class WalletBuilder extends AbstractBuilder<Wallet> {

    private WalletBuilder(final Wallet wallet) {
        super(wallet);
    }

    public static WalletBuilder create() {
        return new WalletBuilder(new Wallet());
    }

    public WalletBuilder name(final String name) {
        this.target.name = name;
        return this;
    }

    public WalletBuilder description(final String description) {
        this.target.description = description;
        return this;
    }

    public WalletBuilder color(final String color) {
        this.target.color = color;
        return this;
    }
}

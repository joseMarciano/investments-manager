package com.investment.managment.stock;

import com.investment.managment.AggregateRoot;
import com.investment.managment.util.InstantUtil;

import java.time.Instant;
import java.util.Objects;

public class Stock extends AggregateRoot<StockID> {

    private final StockID id;

    protected String symbol;

    private final Instant createdAt;

    private Instant updatedAt;


    protected Stock() {
        final Instant now = InstantUtil.now();
        this.id = StockID.unique();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Stock(final StockID anId, final String aSymbol, final Instant createdAt, final Instant updatedAt) {
        this.id = anId;
        this.symbol = aSymbol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Stock with(final StockID anId, final String aSymbol, final Instant createdAt, final Instant updatedAt) {
        return new Stock(anId, aSymbol, createdAt, updatedAt);
    }

    public Stock update(final String aSymbol) {
        this.symbol = aSymbol;
        this.updatedAt = InstantUtil.now();
        return StockBuilder.from(this).build();
    }

    @Override
    public StockID getId() {
        return this.id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Stock stock = (Stock) o;
        return getId().equals(stock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

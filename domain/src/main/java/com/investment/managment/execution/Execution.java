package com.investment.managment.execution;

import com.investment.managment.AggregateRoot;
import com.investment.managment.stock.StockID;
import com.investment.managment.util.InstantUtil;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static java.math.BigDecimal.valueOf;

public class Execution extends AggregateRoot<ExecutionID> {

    private final ExecutionID id;

    protected ExecutionID origin;

    protected StockID stockId;

    protected WalletID walletId;

    protected Double profitPercentage;

    protected Long executedQuantity;

    protected BigDecimal executedPrice;

    private BigDecimal executedVolume;

    protected ExecutionStatus status;

    protected Instant executedAt;

    private final Instant createdAt;

    private Instant updatedAt;


    protected Execution() {
        final Instant now = InstantUtil.now();
        this.id = ExecutionID.unique();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Execution(final ExecutionID id,
                      final ExecutionID origin,
                      final StockID stockId,
                      final WalletID walletId,
                      final Double profitPercentage,
                      final Long executedQuantity,
                      final BigDecimal executedPrice,
                      final BigDecimal executedVolume,
                      final ExecutionStatus status,
                      final Instant executedAt,
                      final Instant createdAt,
                      final Instant updatedAt) {
        this.id = id;
        this.origin = origin;
        this.stockId = stockId;
        this.walletId = walletId;
        this.profitPercentage = profitPercentage;
        this.executedQuantity = executedQuantity;
        this.executedPrice = executedPrice;
        this.executedVolume = executedVolume;
        this.status = status;
        this.executedAt = executedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Execution with(final ExecutionID id,
                                 final ExecutionID origin,
                                 final StockID stockId,
                                 final WalletID walletId,
                                 final Double profitPercentage,
                                 final Long executedQuantity,
                                 final BigDecimal executedPrice,
                                 final BigDecimal executedVolume,
                                 final ExecutionStatus status,
                                 final Instant executedAt,
                                 final Instant createdAt,
                                 final Instant updatedAt) {
        return new Execution(id,
                origin,
                stockId,
                walletId,
                profitPercentage,
                executedQuantity,
                executedPrice,
                executedVolume,
                status,
                executedAt,
                createdAt,
                updatedAt);
    }

    public static Execution with(final ExecutionID id) {
        return with(id, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Execution update(
            final ExecutionID origin,
            final StockID stockId,
            final WalletID walletId,
            final Double profitPercentage,
            final Long executedQuantity,
            final BigDecimal executedPrice,
            final ExecutionStatus status,
            final Instant executedAt
    ) {
        this.origin = origin;
        this.stockId = stockId;
        this.walletId = walletId;
        this.profitPercentage = profitPercentage;
        this.executedQuantity = executedQuantity;
        this.executedPrice = executedPrice;
        this.status = status;
        this.executedAt = executedAt;
        this.updatedAt = InstantUtil.now();
        return ExecutionBuilder.from(this).build();
    }

    protected void calculateExecutedVolume() {
        this.executedVolume = this.executedPrice.multiply(valueOf(this.executedQuantity));
    }

    @Override
    public ExecutionID getId() {
        return this.id;
    }

    public StockID getStockId() {
        return stockId;
    }

    public WalletID getWalletId() {
        return walletId;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public ExecutionID getOrigin() {
        return origin;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Long getExecutedQuantity() {
        return executedQuantity;
    }

    public BigDecimal getExecutedPrice() {
        return executedPrice;
    }

    public BigDecimal getExecutedVolume() {
        return executedVolume;
    }

    public Instant getExecutedAt() {
        return executedAt;
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
        final Execution execution = (Execution) o;
        return getId().equals(execution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

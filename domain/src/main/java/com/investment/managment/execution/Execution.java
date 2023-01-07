package com.investment.managment.execution;

import com.investment.managment.AggregateRoot;
import com.investment.managment.stock.StockID;
import com.investment.managment.util.InstantUtil;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import static com.investment.managment.execution.ExecutionStatus.BUY;
import static com.investment.managment.execution.ExecutionStatus.SELL;
import static java.math.BigDecimal.valueOf;

public class Execution extends AggregateRoot<ExecutionID> {

    private final ExecutionID id;

    protected ExecutionID origin;

    protected StockID stockId;

    protected WalletID walletId;

    protected Double profitPercentage;

    protected Long buyExecutedQuantity;

    protected BigDecimal buyExecutedPrice;

    private BigDecimal buyExecutedVolume;

    protected Long sellExecutedQuantity;

    protected BigDecimal sellExecutedPrice;

    private BigDecimal sellExecutedVolume;

    protected ExecutionStatus status;

    private final Instant createdAt;

    private Instant updatedAt;


    protected Execution() {
        final Instant now = InstantUtil.now();
        this.id = ExecutionID.unique();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Execution(final ExecutionID anId, final Instant createdAt, final Instant updatedAt) {
        this.id = anId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Execution with(final ExecutionID anId, final Instant createdAt, final Instant updatedAt) {
        return new Execution(anId, createdAt, updatedAt);
    }

    public Execution update() {
        this.updatedAt = InstantUtil.now();
        return ExecutionBuilder.from(this).build();
    }

    protected void calculateBuyExecutedVolume() {
        this.buyExecutedVolume = BUY.equals(this.status)
                ? this.buyExecutedPrice.multiply(valueOf(this.buyExecutedQuantity))
                : null;
    }

    protected void calculateSellExecutedVolume() {
        this.sellExecutedVolume = SELL.equals(this.status)
                ? this.sellExecutedPrice.multiply(valueOf(this.sellExecutedQuantity))
                : null;
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

    public Long getBuyExecutedQuantity() {
        return buyExecutedQuantity;
    }

    public Long getSellExecutedQuantity() {
        return sellExecutedQuantity;
    }

    public BigDecimal getBuyExecutedPrice() {
        return buyExecutedPrice;
    }

    public BigDecimal getSellExecutedPrice() {
        return sellExecutedPrice;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public ExecutionID getOrigin() {
        return origin;
    }

    public BigDecimal getBuyExecutedVolume() {
        return buyExecutedVolume;
    }

    public BigDecimal getSellExecutedVolume() {
        return sellExecutedVolume;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
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

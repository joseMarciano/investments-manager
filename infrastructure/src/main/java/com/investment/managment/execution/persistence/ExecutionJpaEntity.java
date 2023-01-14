package com.investment.managment.execution.persistence;

import com.investment.managment.Identifier;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.wallet.WalletID;
import com.investment.managment.wallet.persistence.WalletJpaEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Entity(name = "Execution")
@Table(name = "EXECUTIONS")
@NoArgsConstructor
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExecutionJpaEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_ORIGINS", referencedColumnName = "ID")
    private ExecutionJpaEntity origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_STOCKS", referencedColumnName = "ID")
    private StockJpaEntity stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I_WALLETS", referencedColumnName = "ID")
    private WalletJpaEntity wallet;

    @Column(name = "PROFIT_PERCENTAGE")
    private Double profitPercentage;

    @Column(name = "BUY_EXECUTED_QUANTITY")
    private Long buyExecutedQuantity;

    @Column(name = "BUY_EXECUTED_PRICE")
    private BigDecimal buyExecutedPrice;

    @Column(name = "BUY_EXECUTED_VOLUME")
    private BigDecimal buyExecutedVolume;

    @Column(name = "SELL_EXECUTED_QUANTITY")
    private Long sellExecutedQuantity;

    @Column(name = "SELL_EXECUTED_PRICE")
    private BigDecimal sellExecutedPrice;

    @Column(name = "SELL_EXECUTED_VOLUME")
    private BigDecimal sellExecutedVolume;

    @Column(name = "STATUS")
    private ExecutionStatus status;

    @Column(name = "BOUGHT_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant boughtAt;

    @Column(name = "SOLD_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant soldAt;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    public Execution toAggregate() {
        return Execution.with(
                ExecutionID.from(this.id),
                ofNullable(this.origin).map(ExecutionJpaEntity::getId).map(ExecutionID::from).orElse(null),
                StockID.from(this.stock.getId()),
                WalletID.from(this.wallet.getId()),
                this.profitPercentage,
                this.buyExecutedQuantity,
                this.buyExecutedPrice,
                this.buyExecutedVolume,
                this.sellExecutedQuantity,
                this.sellExecutedPrice,
                this.sellExecutedVolume,
                this.status,
                this.boughtAt,
                this.soldAt,
                this.createdAt,
                this.updatedAt
        );
    }

    public static ExecutionJpaEntity from(final Execution anExecution) {
        return ExecutionJpaEntity.builder()
                .id(anExecution.getId().getValue())
                .origin(ExecutionJpaEntity.withID(anExecution.getOrigin()))
                .stock(StockJpaEntity.withID(anExecution.getStockId()))
                .wallet(WalletJpaEntity.withID(anExecution.getWalletId()))
                .profitPercentage(anExecution.getProfitPercentage())
                .buyExecutedQuantity(anExecution.getBuyExecutedQuantity())
                .buyExecutedPrice(anExecution.getBuyExecutedPrice())
                .buyExecutedVolume(anExecution.getBuyExecutedVolume())
                .sellExecutedQuantity(anExecution.getSellExecutedQuantity())
                .sellExecutedPrice(anExecution.getSellExecutedPrice())
                .sellExecutedVolume(anExecution.getSellExecutedVolume())
                .status(anExecution.getStatus())
                .boughtAt(anExecution.getBoughtAt())
                .soldAt(anExecution.getSoldAt())
                .createdAt(anExecution.getCreatedAt())
                .updatedAt(anExecution.getUpdatedAt())
                .build();
    }

    public static ExecutionJpaEntity withID(final ExecutionID anId) {
        final Function<String, ExecutionJpaEntity> mapToExecutionID = executionID -> ExecutionJpaEntity
                .builder()
                .id(executionID)
                .build();

        return ofNullable(anId)
                .map(Identifier::getValue)
                .map(mapToExecutionID)
                .orElse(null);
    }
}



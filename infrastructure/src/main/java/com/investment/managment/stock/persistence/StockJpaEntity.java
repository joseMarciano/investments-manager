package com.investment.managment.stock.persistence;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity(name = "Stock")
@Table(name = "STOCKS")
@NoArgsConstructor
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockJpaEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    public Stock toAggregate() {
        return Stock.with(
                StockID.from(this.id),
                this.symbol,
                this.createdAt,
                this.updatedAt
        );
    }

    public static StockJpaEntity from(final Stock aStock) {
        return StockJpaEntity.builder()
                .id(aStock.getId().getValue())
                .symbol(aStock.getSymbol())
                .createdAt(aStock.getCreatedAt())
                .updatedAt(aStock.getUpdatedAt())
                .build();
    }
}

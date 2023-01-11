package com.investment.managment.wallet.persistence;

import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity(name = "Wallet")
@Table(name = "WALLETS")
@NoArgsConstructor
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletJpaEntity {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    public Wallet toAggregate() {
        return Wallet.with(
                WalletID.from(this.id),
                this.name,
                this.description,
                this.color,
                this.createdAt,
                this.updatedAt
        );
    }

    public static WalletJpaEntity from(final Wallet aWallet) {
        return WalletJpaEntity.builder()
                .id(aWallet.getId().getValue())
                .name(aWallet.getName())
                .description(aWallet.getDescription())
                .color(aWallet.getColor())
                .createdAt(aWallet.getCreatedAt())
                .updatedAt(aWallet.getUpdatedAt())
                .build();
    }

    public static WalletJpaEntity withID(final WalletID anId) {
        return WalletJpaEntity
                .builder()
                .id(anId.getValue())
                .build();
    }
}

package com.investment.managment.wallet.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity(name = "Wallet")
@Table(name = "WALLETS")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
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
}

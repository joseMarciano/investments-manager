package com.investment.managment.wallet.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletJpaEntity, String> {
}

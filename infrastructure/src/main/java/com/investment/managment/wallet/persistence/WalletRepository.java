package com.investment.managment.wallet.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WalletRepository extends JpaRepository<WalletJpaEntity, String>, JpaSpecificationExecutor<WalletJpaEntity> {
}

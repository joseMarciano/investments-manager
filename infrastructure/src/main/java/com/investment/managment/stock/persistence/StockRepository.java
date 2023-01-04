package com.investment.managment.stock.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockJpaEntity, String>, JpaSpecificationExecutor<StockJpaEntity> {

    Optional<StockJpaEntity> findBySymbol(String symbol);
}

package com.investment.managment.stock.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockRepository extends JpaRepository<StockJpaEntity, String>, JpaSpecificationExecutor<StockJpaEntity> {
}

package com.investment.managment.execution.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExecutionQuantityTotalizerRepository extends JpaRepository<ExecutionQuantityTotalizerJpaEntity, String>, JpaSpecificationExecutor<ExecutionQuantityTotalizerJpaEntity> {
}

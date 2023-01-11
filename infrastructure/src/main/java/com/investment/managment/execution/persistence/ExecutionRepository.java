package com.investment.managment.execution.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExecutionRepository extends JpaRepository<ExecutionJpaEntity, String>, JpaSpecificationExecutor<ExecutionJpaEntity> {
}

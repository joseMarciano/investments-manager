package com.investment.managment.execution.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface ExecutionRepository extends JpaRepository<ExecutionJpaEntity, String>, JpaSpecificationExecutor<ExecutionJpaEntity> {
    boolean existsByOrigin_IdIn(Set<String> ids);

    List<ExecutionJpaEntity> findByOrigin_Id(String id);
}

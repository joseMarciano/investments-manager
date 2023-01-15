package com.investment.managment.execution.persistence;

import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ExecutionRepository extends JpaRepository<ExecutionJpaEntity, String>, JpaSpecificationExecutor<ExecutionJpaEntity> {
    @Query("""
            select new com.investment.managment.execution.summary.ExecutionSummaryByStock(e.stock.symbol as symbol)
            from Execution e
            join e.stock stock
            where (e.wallet.id = :walletId)
            group by stock.symbol
            """)
    List<ExecutionSummaryByStock> getExecutionSummaryByStock(@Param("walletId") String walletId);

    boolean existsByOrigin_IdIn(Set<String> ids);

    List<ExecutionJpaEntity> findByOrigin_Id(String id);
}

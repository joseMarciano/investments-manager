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
            select new com.investment.managment.execution.summary.ExecutionSummaryByStock(
            s.id,
            e.stock.symbol,
            (select coalesce(sum(e.executedQuantity), 0) from Execution e where e.stock.id = s.id  and e.wallet.id = :walletId),
            (select coalesce(sum(e.executedQuantity), 0) from Execution e where e.stock.id = s.id  and e.wallet.id = :walletId and e.status = '1')
            )
            from Execution e
            join e.stock s
            where (e.wallet.id = :walletId)
            group by s.id,s.symbol
            """)
    List<ExecutionSummaryByStock> getExecutionSummaryByStock(@Param("walletId") String walletId);

    //(select 1 from Execution exec where exec.stock.id = s.id  and exec.wallet.id = :walletId)
    boolean existsByOrigin_IdIn(Set<String> ids);

    List<ExecutionJpaEntity> findByOrigin_Id(String id);
}

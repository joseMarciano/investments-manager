package com.investment.managment.execution;

import com.investment.managment.execution.persistence.ExecutionJpaEntity;
import com.investment.managment.execution.persistence.ExecutionRepository;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExecutionPostgresGateway implements ExecutionGateway {

    private final ExecutionRepository executionRepository;

    public ExecutionPostgresGateway(final ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    @Override
    public Execution create(final Execution anExecution) {
        return save(anExecution);
    }

    @Override
    public Execution update(final Execution anExecution) {
        return save(anExecution);
    }

    @Override
    public Optional<Execution> findById(final ExecutionID anId) {
        return this.executionRepository.findById(anId.getValue()).map(ExecutionJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Execution> findAll(final SearchQuery query) {
        return null;
    }

    @Override
    public void deleteById(final ExecutionID id) {

    }

    private Execution save(final Execution anExecution) {
        return this.executionRepository.save(ExecutionJpaEntity.from(anExecution)).toAggregate();
    }
}

package com.investment.managment.execution;

import com.investment.managment.execution.persistence.ExecutionJpaEntity;
import com.investment.managment.execution.persistence.ExecutionRepository;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Execution> findAllByOriginId(final ExecutionID origin) {
        return this.executionRepository.findByOrigin_Id(origin.getValue()).stream().map(ExecutionJpaEntity::toAggregate).toList();
    }

    @Override
    public boolean existsByOriginId(final ExecutionID... originIds) {
        final var ids =
                Optional.ofNullable(originIds)
                        .map(it -> Arrays.stream(it).map(ExecutionID::getValue).collect(Collectors.toSet()))
                        .orElse(Collections.emptySet());
        return this.executionRepository.existsByOrigin_IdIn(ids);
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

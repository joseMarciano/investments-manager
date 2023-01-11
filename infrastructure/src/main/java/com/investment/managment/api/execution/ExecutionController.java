package com.investment.managment.api.execution;

import com.investment.managment.api.execution.models.CreateExecutionRequest;
import com.investment.managment.api.execution.models.CreateExecutionResponse;
import com.investment.managment.api.execution.models.UpdateExecutionRequest;
import com.investment.managment.api.execution.models.UpdateExecutionResponse;
import com.investment.managment.execution.create.CreateExecutionCommandInput;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.presenters.ExecutionAPIPresenter;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController implements ExecutionAPI {

    private final UpdateExecutionUseCase updateExecutionUseCase;
    private final CreateExecutionUseCase createExecutionUseCase;

    public ExecutionController(final UpdateExecutionUseCase updateExecutionUseCase,
                               final CreateExecutionUseCase createExecutionUseCase) {
        this.updateExecutionUseCase = updateExecutionUseCase;
        this.createExecutionUseCase = createExecutionUseCase;
    }

    @Override
    public CreateExecutionResponse create(final CreateExecutionRequest executionRequest) {
        return ExecutionAPIPresenter.present(this.createExecutionUseCase.execute(CreateExecutionCommandInput.with(
                executionRequest.stockId(),
                executionRequest.walletId(),
                executionRequest.profitPercentage(),
                executionRequest.buyExecutedQuantity(),
                executionRequest.buyExecutedPrice(),
                executionRequest.boughtAt()
        )));
    }

    @Override
    public UpdateExecutionResponse update(final String id, final UpdateExecutionRequest executionRequest) {
        return ExecutionAPIPresenter.present(this.updateExecutionUseCase.execute(UpdateExecutionCommandInput.with(
                executionRequest.id(),
                executionRequest.stockId(),
                executionRequest.executedQuantity(),
                executionRequest.executedPrice(),
                executionRequest.profitPercentage(),
                executionRequest.executedAt()
        )));
    }
}

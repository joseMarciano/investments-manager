package com.investment.managment.api.execution;

import com.investment.managment.api.execution.models.*;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.create.CreateExecutionCommandInput;
import com.investment.managment.execution.create.CreateExecutionUseCase;
import com.investment.managment.execution.findById.FindExecutionByIdUseCase;
import com.investment.managment.execution.presenters.ExecutionAPIPresenter;
import com.investment.managment.execution.summarybystock.SummaryExecutionUseCase;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionUseCase;
import com.investment.managment.stock.deleteById.DeleteExecutionByIdUseCase;
import com.investment.managment.wallet.WalletID;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExecutionController implements ExecutionAPI {

    private final UpdateExecutionUseCase updateExecutionUseCase;
    private final CreateExecutionUseCase createExecutionUseCase;
    private final FindExecutionByIdUseCase findExecutionByIdUseCase;
    private final SummaryExecutionUseCase summaryExecutionUseCase;
    private final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase;

    public ExecutionController(final UpdateExecutionUseCase updateExecutionUseCase,
                               final CreateExecutionUseCase createExecutionUseCase,
                               final FindExecutionByIdUseCase findExecutionByIdUseCase,
                               final SummaryExecutionUseCase summaryExecutionUseCase,
                               final DeleteExecutionByIdUseCase deleteExecutionByIdUseCase) {
        this.updateExecutionUseCase = updateExecutionUseCase;
        this.createExecutionUseCase = createExecutionUseCase;
        this.findExecutionByIdUseCase = findExecutionByIdUseCase;
        this.summaryExecutionUseCase = summaryExecutionUseCase;
        this.deleteExecutionByIdUseCase = deleteExecutionByIdUseCase;
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
                executionRequest.executedQuantity(),
                executionRequest.executedPrice(),
                executionRequest.profitPercentage(),
                executionRequest.executedAt()
        )));
    }

    @Override
    public FindByIdExecutionResponse findById(final String id) {
        return ExecutionAPIPresenter.present(this.findExecutionByIdUseCase.execute(ExecutionID.from(id)));
    }

    @Override
    public List<SummaryExecutionByStockResponse> getSummaryByStock(final String walletId) {
        return this.summaryExecutionUseCase.execute(WalletID.from(walletId))
                .stream()
                .map(ExecutionAPIPresenter::present)
                .toList();
    }

    @Override
    public void deleteById(final String id) {
        this.deleteExecutionByIdUseCase.execute(ExecutionID.from(id));
    }
}

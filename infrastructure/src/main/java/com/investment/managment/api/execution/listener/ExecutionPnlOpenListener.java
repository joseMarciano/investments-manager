package com.investment.managment.api.execution.listener;

import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.models.ExecutionPnlRequest;
import com.investment.managment.execution.updatePnl.UpdateExecutionPnlOpenCommand;
import com.investment.managment.execution.updatePnl.UpdateExecutionPnlOpenUseCase;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ExecutionPnlOpenListener {

    private final UpdateExecutionPnlOpenUseCase updateExecutionPnlOpenUseCase;

    public ExecutionPnlOpenListener(final UpdateExecutionPnlOpenUseCase updateExecutionPnlOpenUseCase) {
        this.updateExecutionPnlOpenUseCase = updateExecutionPnlOpenUseCase;
    }

    @SqsListener(value = "${aws.sqs.execution-pnl-open-changed-queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void executionChangedListener(final @Payload ExecutionPnlRequest request) {
        updateExecutionPnlOpenUseCase.execute(UpdateExecutionPnlOpenCommand.with(ExecutionID.from(request.id()), request.pnl()));
    }
}

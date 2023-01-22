package com.investment.managment.execution.gateway.sqs;

import com.investment.managment.config.aws.AWSContext;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.gateway.CreateExecutionGateway;
import com.investment.managment.execution.gateway.DeleteIdExecutionGateway;
import com.investment.managment.execution.gateway.UpdateExecutionGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AWSContext
public class ExecutionSQSGateway implements
        CreateExecutionGateway,
        DeleteIdExecutionGateway,
        UpdateExecutionGateway {

    private final String executionEventChangedQueue;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public ExecutionSQSGateway(final @Value("${aws.sqs.execution-event-changed-queue}") String executionEventChangedQueue,
                               final QueueMessagingTemplate queueMessagingTemplate) {
        this.executionEventChangedQueue = executionEventChangedQueue;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @Override
    public Execution create(final Execution anExecution) {
        sendToQueue(anExecution, ExecutionChangeReason.CREATED);
        return anExecution;
    }

    @Override
    public void deleteById(final ExecutionID id) {
        sendToQueue(Execution.with(id), ExecutionChangeReason.DELETED);
    }

    @Override
    public Execution update(final Execution anExecution) {
        sendToQueue(anExecution, ExecutionChangeReason.UPDATED);
        return anExecution;
    }

    private void sendToQueue(final Execution anExecution, final ExecutionChangeReason reason) {
        final var message = ExecutionChangedMessage.of(anExecution, reason);
        this.queueMessagingTemplate
                .convertAndSend(this.executionEventChangedQueue, message);
    }
}

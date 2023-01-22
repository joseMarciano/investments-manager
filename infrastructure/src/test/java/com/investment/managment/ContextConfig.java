package com.investment.managment;

import com.investment.managment.execution.gateway.sqs.ExecutionSQSGateway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class ContextConfig {

    @Bean
    public ExecutionSQSGateway sqsGatewayMock() {
        return mock(ExecutionSQSGateway.class);
    }
}

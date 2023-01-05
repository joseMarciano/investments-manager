package com.investment.managment.config.scheduling;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ConditionalOnProperty(prefix = "app-context.scheduler", name = "disabled", havingValue = "false")
@EnableScheduling
public class SchedulingConfig {
}

package com.investment.managment;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(DataBaseExtension.class)
@Import(FlywayConfig.class)
public class DataBaseExtension implements BeforeEachCallback {

    private static final String DATABASE_NAME = "investment-management";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "123456";
    @Container
    private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:15")
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER)
            .withPassword(DATABASE_PASSWORD);

    @DynamicPropertySource
    public static void registerDataBaseContainer(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgresqlContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgresqlContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgresqlContainer.getPassword());
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        final ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        executeMigration(applicationContext);
    }

    private static void executeMigration(final ApplicationContext applicationContext) {
        final var flyway = applicationContext.getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }

}

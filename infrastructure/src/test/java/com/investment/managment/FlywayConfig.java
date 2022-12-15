package com.investment.managment;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class FlywayConfig {

    @Bean
    public Flyway flyway(final DataSource dataSource) {
        final var config = new FluentConfiguration()
                .dataSource(dataSource)
                .cleanDisabled(false); // allow execute clean programmatically
//                .skipExecutingMigrations(true) only in teams edition. But its good to disable auto migration.

        return new Flyway(config);
    }
}

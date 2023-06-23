package de.civento.eahtools.routingrepository.db.migration;

import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
class FlywayConfiguration implements FlywayConfigurationCustomizer {
    private final ApplicationContext applicationContext;

    public FlywayConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void customize(FluentConfiguration configuration) {
        JavaMigration[] migrationBeans = applicationContext
         .getBeansOfType(JavaMigration.class)
         .values().toArray(new JavaMigration[0]);
        configuration.javaMigrations(migrationBeans);
    }
}
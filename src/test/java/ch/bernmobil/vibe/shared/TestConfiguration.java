package ch.bernmobil.vibe.shared;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

@Configuration
@Profile("testConfiguration")
public class TestConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UpdateManagerRepository updateManagerRepository() {
        return new UpdateManagerRepository(mockedDslContext());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UpdateHistoryRepository updateHistoryRepository() {
        return new UpdateHistoryRepository(mockedDslContext());
    }


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DSLContext mockedDslContext() {
        MockDataProvider provider = mockProvider();
        MockConnection connection = new MockConnection(provider);
        return DSL.using(connection, SQLDialect.POSTGRES);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MockProvider mockProvider() {
        return new MockProvider();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UpdateManager updateManager() {
        return new UpdateManager(updateManagerRepository(), updateManagerRepository(), updateHistoryRepository(), 2,
                Duration.ofMinutes(30), updateTimestampManager());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UpdateTimestampManager updateTimestampManager() {
        return new UpdateTimestampManager();
    }

}
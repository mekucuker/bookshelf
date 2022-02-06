package tr.com.mek.bookshelf.integration.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static DatabaseContainer databaseContainer;

    public DatabaseInitializer() {
        databaseContainer = new DatabaseContainer();
        databaseContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues values =
                TestPropertyValues.of("spring.data.mongodb.host = " + databaseContainer.getContainerIpAddress(),
                        "spring.data.mongodb.port = " + databaseContainer.getPort());
        values.applyTo(configurableApplicationContext);
    }
}
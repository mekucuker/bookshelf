package tr.com.mek.bookshelf.integration.initializer;

import org.testcontainers.containers.GenericContainer;

public class DatabaseContainer extends GenericContainer<DatabaseContainer> {

    public static final String DOCKER_IMAGE = "mongo";
    public static final String DB_ROOT_USERNAME = "test";
    public static final String DB_ROOT_PASSWORD = "test";
    public static final int DB_PORT = 27017;

    public DatabaseContainer() {
        super(DOCKER_IMAGE);
        addEnv("MONGO_INITDB_ROOT_USERNAME", DB_ROOT_USERNAME);
        addEnv("MONGO_INITDB_ROOT_PASSWORD", DB_ROOT_PASSWORD);
        addExposedPort(DB_PORT);
    }

    public Integer getPort() {
        return getMappedPort(DB_PORT);
    }
}

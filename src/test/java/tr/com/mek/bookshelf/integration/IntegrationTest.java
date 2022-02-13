package tr.com.mek.bookshelf.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import tr.com.mek.bookshelf.integration.initializer.DatabaseInitializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to use for integration tests.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = DatabaseInitializer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IntegrationTest {

}

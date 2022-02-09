package tr.com.mek.bookshelf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${spring.mvc.servlet.path}")
    private String servletPath;

    private final BuildProperties buildProperties;

    @Bean
    OpenAPI openAPI() {
        Info info = getInfo();
        Server server = new Server()
                .url(contextPath + servletPath);

        return new OpenAPI()
                .info(info)
                .servers(Collections.singletonList(server));
    }

    private Info getInfo() {
        String applicationName = buildProperties.getName().toUpperCase();
        String applicationVersion = buildProperties.getVersion();
        return new Info()
                .title(applicationName)
                .version(applicationVersion)
                .description("RestAPI Documentation");
    }
}

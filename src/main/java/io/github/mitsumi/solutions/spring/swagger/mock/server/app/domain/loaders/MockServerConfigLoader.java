package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.MockServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Load mock server config file.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MockServerConfigLoader {
    /**
     * config file.
     */
    @Value("${spring.swagger.mock-server.config-file}")
    private String configFile;

    /**
     * The json mapper.
     */
    private final JsonMapper jsonMapper;

    /**
     * load a mock server config file.
     *
     * @return loaded configs.
     */
    @SneakyThrows
    public List<MockServerConfig> load() {
        return jsonMapper.readValue(Files.readString(Path.of(configFile)), new TypeReference<>() {});
    }
}

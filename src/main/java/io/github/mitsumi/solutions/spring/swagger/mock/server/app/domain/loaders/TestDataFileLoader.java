package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.TestDataFileInfo;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.ValueResolver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

/**
 * Load test data file.
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class TestDataFileLoader {

    /**
     * The json mapper.
     */
    private final JsonMapper jsonMapper;

    /**
     * The value resolver.
     */
    private final Set<ValueResolver> valueResolvers;

    /**
     * load default test data file.
     *
     * @param testDataFileInfo test data
     * @return loaded
     */
    public Map<String, Object> loadDefault(final TestDataFileInfo testDataFileInfo,
                                           final Map<String, Object> parameters) {
        final var path = Path.of(testDataFileInfo.directory(), testDataFileInfo.defaultSuccessDataFilename());
        return load(path, parameters);
    }

    /**
     * load test data file.
     *
     * @param testDataFileInfo test data
     * @return loaded
     */
    public Map<String, Object> loadTestDataFile(final TestDataFileInfo testDataFileInfo,
                                                final Map<String, Object> parameters) {
        final var path = Path.of(testDataFileInfo.directory(), testDataFileInfo.filename());
        return load(path, parameters);
    }

    @SneakyThrows
    private Map<String, Object> load(final Path path, final Map<String, Object> parameters) {
        var content = Files.readString(path);

        for (final var valueResolver : valueResolvers) {
            content = valueResolver.resolve(content, parameters);
        }

        return jsonMapper.readValue(content, new TypeReference<>() {
        });
    }

}

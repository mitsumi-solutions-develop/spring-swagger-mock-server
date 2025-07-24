package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.mitsumi.solutions.spring.json.Jsons;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.TestDataFileInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Load test data file.
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public class TestDataFileLoader {

    /**
     * jsons.
     */
    private final Jsons jsons;

    /**
     * load default test data file.
     *
     * @param testDataFileInfo test data
     * @return loaded
     */
    public Map<String, Object> loadDefault(final TestDataFileInfo testDataFileInfo) {
        final var path = Path.of(testDataFileInfo.directory(), testDataFileInfo.defaultSuccessDataFilename());
        return load(path);
    }

    /**
     * load test data file.
     *
     * @param testDataFileInfo test data
     * @return loaded
     */
    public Map<String, Object> loadTestDataFile(final TestDataFileInfo testDataFileInfo) {
        final var path = Path.of(testDataFileInfo.directory(), testDataFileInfo.filename());
        return load(path);
    }

    @SneakyThrows
    private Map<String, Object> load(final Path path) {
        return jsons.deserialize(Files.readString(path), new TypeReference<>() {
        });
    }

}

package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.TestSpringSwaggerMockServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestSpringSwaggerMockServerApplication.class, properties = {
    "spring.swagger.mock-server.config-file=./src/test/resources/test-data/unit-test/io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.TestDataFileLoaderTest/mock-server.config.json"
})
@ActiveProfiles("unit-test")
public class TestDataFileLoaderTest {

    @Autowired
    private MockServerConfigLoader mockServerConfigLoader;

    @Autowired
    private TestDataFileLoader testDataFileLoader;

    @Test
    public void test_loadDefault() {
        var testDataFileInfo = mockServerConfigLoader.load().getFirst().testDataFileInfo();
        var actual = testDataFileLoader.loadDefault(testDataFileInfo);

        assertThat(actual).isNotNull();

        assertThat(actual.get("response")).isInstanceOf(Map.class);

        Map<String, Object> actualResponse = cast(actual.get("response"));

        assertThat(actualResponse.get("count")).isEqualTo(2);
        assertThat(actualResponse.get("results")).isInstanceOf(List.class);

        List<Map<String, Object>> actualResults = cast(actualResponse.get("results"));

        assertThat(actualResults).hasSize(2);

        assertThat(actualResults.getFirst().get("id")).isEqualTo(1);
        assertThat(actualResults.getFirst().get("name")).isEqualTo("test company 1");
        assertThat(actualResults.getFirst().get("address")).isEqualTo("test address 1");
    }

    @Test
    public void test_loadTestDataFile() {
        var testDataFileInfo = mockServerConfigLoader.load().getFirst().testDataFileInfo();
        var actual = testDataFileLoader.loadTestDataFile(testDataFileInfo);

        assertThat(actual).isNotNull();

        assertThat(actual.get("cd9b5011-42d9-44b4-b760-b0794ae2e728")).isInstanceOf(Map.class);

        Map<String, Object> actualFirst = cast(actual.get("cd9b5011-42d9-44b4-b760-b0794ae2e728"));

        assertThat(actualFirst.get("status")).isEqualTo(200);
        assertThat(actualFirst.get("response")).isInstanceOf(Map.class);

        Map<String, Object> actualResponse = cast(actualFirst.get("response"));

        assertThat(actualResponse.get("count")).isEqualTo(2);
        assertThat(actualResponse.get("results")).isInstanceOf(List.class);

        List<Map<String, Object>> actualResults = cast(actualResponse.get("results"));

        assertThat(actualResults).hasSize(2);

        assertThat(actualResults.getFirst().get("id")).isEqualTo(1);
        assertThat(actualResults.getFirst().get("name")).isEqualTo("test company 1");
        assertThat(actualResults.getFirst().get("address")).isEqualTo("test address 1");
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object object) {
        return (T) object;
    }
}

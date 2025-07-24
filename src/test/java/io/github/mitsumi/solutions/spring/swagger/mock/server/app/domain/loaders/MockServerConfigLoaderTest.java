package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.TestSpringSwaggerMockServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestSpringSwaggerMockServerApplication.class, properties = {
    "spring.swagger.mock-server.config-file=./src/test/resources/test-data/unit-test/io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.MockServerConfigLoaderTest/mock-server.config.json"
})
@ActiveProfiles("unit-test")
public class MockServerConfigLoaderTest {

    @Autowired
    private MockServerConfigLoader mockServerConfigLoader;

    @Test
    public void test_load() {
        var actual = mockServerConfigLoader.load();

        assertThat(actual).isNotNull();

        assertThat(actual).hasSize(5);

        assertThat(actual.getFirst().requestUri()).isEqualTo("/companies");
        assertThat(actual.getFirst().requestMethod()).isEqualTo("get");
        assertThat(actual.getFirst().delegatorClass()).isNull();
        assertThat(actual.getFirst().keyParameterExpression()).isEqualTo("['apiKey']");
        assertThat(actual.getFirst().testDataFileInfo().directory())
            .isEqualTo("./var/test-data/companies-api/get-companies");
        assertThat(actual.getFirst().testDataFileInfo().defaultSuccessDataFilename())
            .isEqualTo("default.json");
        assertThat(actual.getFirst().testDataFileInfo().filename())
            .isEqualTo("test-data.json");
    }
}

package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers;

import io.github.mitsumi.solutions.spring.json.Jsons;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.TestDataFileLoader;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.TestDataFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * ResponseEntityのResolver.
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings("PMD.LongVariable")
public class ResponseEntityResolver {

    /**
     * テストデータファイルLoader.
     */
    private final TestDataFileLoader loader;

    /**
     * Jsons.
     */
    private final Jsons jsons;

    /**
     * response entityを解決する.
     *
     * @param keyParameterValue key parameter value
     * @param testDataFileInfo test data file info
     * @param responseBodyTypes response body types
     * @return response entity
     */
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> resolve(final String keyParameterValue,
                                          final TestDataFileInfo testDataFileInfo,
                                          final Map<Integer, Class<?>> responseBodyTypes) {
        final var defaultTestDataMap = loader.loadDefault(testDataFileInfo);
        final var testDataMap = loader.loadTestDataFile(testDataFileInfo);

        final var statusOfResponse = (Map<String, Object>) testDataMap.get(keyParameterValue);

        return Objects.isNull(statusOfResponse) ?
            resolveByDefaultTestData(defaultTestDataMap, responseBodyTypes.get(HttpStatus.OK.value())) :
            resolveByTestData(statusOfResponse, responseBodyTypes);
    }

    private ResponseEntity<Object> resolveByDefaultTestData(final Map<String, Object> defaultTestDataMap,
                                                            final Class<?> successResponseBodyType) {
        final var response = defaultTestDataMap.get("response");

        return Objects.isNull(successResponseBodyType) ?
            ResponseEntity.ok().build() : ResponseEntity.ok(responseBody(response, successResponseBodyType));
    }

    private ResponseEntity<Object> resolveByTestData(final Map<String, Object> responseMap,
                                                     final Map<Integer, Class<?>> responseBodyTypes) {
        final var status = (Integer) responseMap.get("status");
        final var responseBodyType = responseBodyTypes.get(status);

        return Objects.isNull(responseBodyType) ?
            ResponseEntity.status(status).build() :
            ResponseEntity.status(status).body(responseBody(responseMap.get("response"), responseBodyType));

    }

    private Object responseBody(final Object responseBody, final Class<?> responseBodyType) {
        return Objects.isNull(responseBody) ? null : jsons.deserialize(jsons.serialize(responseBody), responseBodyType);
    }
}

package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * Mock server config data class.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@ToString
@NoArgsConstructor
@SuppressWarnings("PMD.LongVariable")
public class MockServerConfig {

    /**
     * request uri.
     */
    @JsonProperty("request_uri")
    private String requestUri;

    /**
     * request method.
     */
    @JsonProperty("request_method")
    private String requestMethod;

    /**
     * 個別の委託クラス.
     */
    @JsonProperty("delegator_class")
    private String delegatorClass;

    /**
     * キーとなるパラメータのspring 評価式.
     */
    @JsonProperty("key_parameter_expression")
    private String keyParameterExpression;

    /**
     * テストデータ情報.
     */
    @JsonProperty("test_data_file_info")
    private TestDataFileInfo testDataFileInfo;
}

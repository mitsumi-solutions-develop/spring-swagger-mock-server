package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * テストデータFile情報のdata class.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
@ToString
@NoArgsConstructor
@SuppressWarnings({"PMD.TestClassWithoutTestCases", "PMD.LongVariable"})
public class TestDataFileInfo {

    /**
     * テストデータFileの格納したデレクトリ.
     */
    @JsonProperty("directory")
    private String directory;

    /**
     * デフォルトの正常のテストデータファイル名.
     */
    @JsonProperty("default_success_data_filename")
    private String defaultSuccessDataFilename;

    /**
     * テストデータファイル名.
     */
    @JsonProperty("filename")
    private String filename;
}

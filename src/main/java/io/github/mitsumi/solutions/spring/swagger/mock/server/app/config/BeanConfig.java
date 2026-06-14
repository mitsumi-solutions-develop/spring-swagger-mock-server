package io.github.mitsumi.solutions.spring.swagger.mock.server.app.config;

import io.github.mitsumi.solutions.shared.parsers.RelativeDateTimeParser;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The bean config class.
 */
@Configuration
@NoArgsConstructor
public class BeanConfig {

    /**
     * The bean definition of RelativeDateTimeParser.
     * @return RelativeDateTimeParser
     */
    @Bean
    public RelativeDateTimeParser relativeDateTimeParser() {
        return RelativeDateTimeParser.build();
    }
}

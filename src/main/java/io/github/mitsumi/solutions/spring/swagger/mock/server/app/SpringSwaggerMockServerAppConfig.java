package io.github.mitsumi.solutions.spring.swagger.mock.server.app;

import io.github.mitsumi.solutions.shared.json.factories.JsonMapperFactory;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.AntPathMatcher;
import tools.jackson.databind.json.JsonMapper;

/**
 * Application config.
 */
@Configuration
@NoArgsConstructor
public class SpringSwaggerMockServerAppConfig {

    /**
     * Bean definition of ExpressionParser.
     * @return ExpressionParser object
     */
    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    /**
     * Bean definition of JsonMapper.
     *
     * @return JsonMapper object
     */
    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapperFactory.build().create();
    }

    /**
     * Bean definition of AntPathMatcher.
     * @return AntPathMatcher object
     */
    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}

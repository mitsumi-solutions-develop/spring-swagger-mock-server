package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.MockServerConfigLoader;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.MockServerConfig;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.ResponseEntityResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Map;

/**
 * デフォルトのResponseEntityを生成する委託者.
 */
@Component
@SuppressWarnings({"PMD.LongVariable", "PMD.CommentSize"})
public class DefaultResponseEntityDelegator extends AbstractResponseEntityDelegator {
    /**
     * Parses expression strings into compiled expressions that can be evaluated.
     */
    private final ExpressionParser expressionParser;

    /**
     * ResponseEntityの解決者.
     */
    private final ResponseEntityResolver responseEntityResolver;

    /**
     * Constructor.
     *
     * @param applicationContext Central interface to provide configuration for an application.
     * @param configLoader mock server config loader
     * @param expressionParser Parses expression strings into compiled expressions that can be evaluated
     * @param antPathMatcher PathMatcher implementation for Ant-style path patterns
     * @param responseEntityResolver ResponseEntityの解決者
     */
    public DefaultResponseEntityDelegator(final ApplicationContext applicationContext,
                                          final MockServerConfigLoader configLoader,
                                          final ExpressionParser expressionParser,
                                          final AntPathMatcher antPathMatcher,
                                          final ResponseEntityResolver responseEntityResolver) {
        super(applicationContext, configLoader, antPathMatcher);

        this.expressionParser = expressionParser;
        this.responseEntityResolver = responseEntityResolver;
    }

    @Override
    protected ResponseEntity<Object> doInternal(final HttpServletRequest request,
                                                final Map<Integer, Class<?>> responseBodyTypes,
                                                final Map<String, Object> parameters,
                                                final MockServerConfig mockServerConfig) {
        final var keyParameterValue = keyParameterValue(parameters, mockServerConfig);
        return responseEntityResolver.resolve(keyParameterValue, mockServerConfig.testDataFileInfo(), responseBodyTypes);
    }

    private String keyParameterValue(final Map<String, Object> parameters,
                                     final MockServerConfig mockServerConfig) {
        final var context = new StandardEvaluationContext(parameters);
        final var expression = expressionParser.parseExpression(mockServerConfig.keyParameterExpression());

        return expression.getValue(context, String.class);
    }
}

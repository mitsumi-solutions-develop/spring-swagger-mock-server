package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.ResponseEntityDelegator;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.loaders.MockServerConfigLoader;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.models.MockServerConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * ResponseEntity生成の委託親クラス.
 */
@RequiredArgsConstructor
@SuppressWarnings("PMD.CommentSize")
public abstract class AbstractResponseEntityDelegator implements ResponseEntityDelegator {

    /**
     * Central interface to provide configuration for an application.
     */
    private final ApplicationContext context;

    /**
     * Mock server config file loader.
     */
    private final MockServerConfigLoader configLoader;

    /**
     * ant path matcher.
     */
    private final AntPathMatcher antPathMatcher;

    /**
     * context path.
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 内部処理.
     *
     * @param request http request
     * @param responseBodyTypes response body types
     * @param parameters parameters
     * @param mockServerConfig mock server config
     * @return response entity
     */
    protected abstract ResponseEntity<Object> doInternal(HttpServletRequest request,
                                                         Map<Integer, Class<?>> responseBodyTypes,
                                                         Map<String, Object> parameters,
                                                         MockServerConfig mockServerConfig);

    @Override
    public ResponseEntity<Object> delegate(final HttpServletRequest request,
                                           final Map<Integer, Class<?>> responseBodyTypes,
                                           final Map<String, Object> parameters) {
        final var config = mockServerConfig(request);

        return delegator(config)
            .map(delegator -> delegator.delegate(request, responseBodyTypes, parameters))
            .orElseGet(() -> doInternal(request, responseBodyTypes, parameters, config));
    }

    private Optional<ResponseEntityDelegator> delegator(final MockServerConfig config) {
        return StringUtils.hasLength(config.delegatorClass()) ?
            Optional.of((ResponseEntityDelegator) context.getBean(classForName(config.delegatorClass()))) :
            Optional.empty();
    }

    @SneakyThrows
    private Class<?> classForName(final String name) {
        return Class.forName(name);
    }

    private MockServerConfig mockServerConfig(final HttpServletRequest request) {
        return configLoader.load().stream()
            .filter(config -> filterMockServerConfig(config, request))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "Mock Server config not found. requestUri : %s, requestMethod : %s".formatted(
                    request.getRequestURI(), request.getMethod()
                )
            ));
    }

    private boolean filterMockServerConfig(final MockServerConfig config, final HttpServletRequest request) {
        return antPathMatcher.match(config.requestUri(), request.getRequestURI().replace(contextPath, "")) &&
               config.requestMethod().equalsIgnoreCase(request.getMethod());
    }
}

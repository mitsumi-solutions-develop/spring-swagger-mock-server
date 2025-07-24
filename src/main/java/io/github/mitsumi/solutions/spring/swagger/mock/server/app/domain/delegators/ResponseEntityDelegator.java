package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * ResponseEntity生成委託者.
 */
@FunctionalInterface
public interface ResponseEntityDelegator {

    /**
     * ResponseEntity生成を委託する.
     *
     * @param request http request
     * @param responseBodyTypes response body types, Integer: status code
     * @param parameters parameters
     * @return response entity
     */
    ResponseEntity<Object> delegate(HttpServletRequest request,
                                    Map<Integer, Class<?>> responseBodyTypes,
                                    Map<String, Object> parameters);
}

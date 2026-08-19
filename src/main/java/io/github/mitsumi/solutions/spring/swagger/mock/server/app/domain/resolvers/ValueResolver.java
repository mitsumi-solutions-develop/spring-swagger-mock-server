package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers;

import java.util.Map;

@FunctionalInterface
@SuppressWarnings("PMD.CommentRequired")
public interface ValueResolver {

    String resolve(String content, Map<String, Object> parameters);
}

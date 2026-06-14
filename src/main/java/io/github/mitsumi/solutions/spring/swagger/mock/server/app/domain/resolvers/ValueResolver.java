package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers;

@FunctionalInterface
@SuppressWarnings("PMD.CommentRequired")
public interface ValueResolver {

    String resolve(String content);
}

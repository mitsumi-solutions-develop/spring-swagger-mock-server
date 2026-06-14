package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.ValueResolver;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.enumerations.UUIDExpression;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@SuppressWarnings({"PMD.CommentRequired"})
@NoArgsConstructor
public class UUIDResolver implements ValueResolver {

    @Override
    public String resolve(final String content) {
        final var lines = content.lines().toList();
        final var appender = new ArrayList<String>();

        for (final var line : lines) {
            appender.add(UUIDExpression.resolve(line));
        }

        return String.join(System.lineSeparator(), appender);
    }
}

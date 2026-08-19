package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.RelativeDateTimeResolver;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.ValueResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@SuppressWarnings("PMD.CommentRequired")
public class DateTimeResolver implements ValueResolver {

    private final Set<RelativeDateTimeResolver> resolvers;

    @Override
    public String resolve(final String content, final Map<String, Object> parameters) {
        var temp = content;

        for (final var resolver : resolvers) {
            temp = resolver.resolve(temp);
        }

        return temp;
    }
}

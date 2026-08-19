package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.ValueResolver;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@SuppressWarnings("PMD.CommentRequired")
public class RequestParameterValueResolver implements ValueResolver {

    private static final Pattern PATTERN = Pattern.compile("\\['[a-zA-Z0-9]+'\\](\\.[a-zA-Z0-9]+)*");
    private final ExpressionParser expressionParser;

    @Override
    public String resolve(final String content, final Map<String, Object> parameters) {
        final var lines = content.lines().toList();
        final var appender = new ArrayList<String>();
        final var context = new StandardEvaluationContext(parameters);

        for (final var line : lines) {
            appender.add(replace(line, context));
        }

        return String.join(System.lineSeparator(), appender);
    }

    private String replace(final String line, final StandardEvaluationContext context) {
        final var matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final var matchedText = matcher.group();
            final var expression = expressionParser.parseExpression(matchedText);

            final var value = expression.getValue(context, String.class);

            return StringUtils.isNotEmpty(value) ?
                line.replace(matchedText, value) : line;
        }

        return line;
    }
}

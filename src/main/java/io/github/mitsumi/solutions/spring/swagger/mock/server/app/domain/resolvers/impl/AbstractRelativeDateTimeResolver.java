package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.shared.parsers.RelativeDateTimeParser;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.RelativeDateTimeResolver;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@SuppressWarnings({"PMD.CommentRequired", "PMD.AvoidReassigningLoopVariables"})
public abstract class AbstractRelativeDateTimeResolver implements RelativeDateTimeResolver {

    private static final String INPUT_PATTERN = "\\[[nN][oO][wW]\\s*(([-+][0-9]+[yMdhms]\\s*)*)([0-9:]*)?]";

    private final RelativeDateTimeParser dateTimeParser;

    protected abstract String expression();

    protected abstract Object resolveValue(LocalDateTime dateTime);

    @Override
    public String resolve(final String content) {
        final var regex = "(?<now>%s)::(?<expression>%s)".formatted(INPUT_PATTERN, expression());
        final var pattern = Pattern.compile(regex);

        final var appender = new ArrayList<String>();

        final var lines = content.lines().toList();

        for (var line : lines) {
            final var matcher = pattern.matcher(line);

            if (matcher.find()) {
                final var nowPattern = matcher.group("now");
                final var resolvedValue = resolveValue(dateTimeParser.parse(nowPattern));

                if (resolvedValue instanceof String value) {
                    line = line.replaceAll(regex, value);
                } else if (resolvedValue instanceof Long value) {
                    line = line.replaceAll("\"%s\"".formatted(regex), value.toString());
                }
            }

            appender.add(line);
        }

        return String.join(System.lineSeparator(), appender);

    }


}

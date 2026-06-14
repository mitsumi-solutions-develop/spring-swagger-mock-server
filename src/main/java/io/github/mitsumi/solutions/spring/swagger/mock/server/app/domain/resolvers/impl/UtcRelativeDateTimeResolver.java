package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.shared.parsers.RelativeDateTimeParser;
import io.github.mitsumi.solutions.shared.utils.DateTimeUtils;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.enumerations.RelativeDateTimeExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@SuppressWarnings("PMD.CommentRequired")
public class UtcRelativeDateTimeResolver extends AbstractRelativeDateTimeResolver {

    public UtcRelativeDateTimeResolver(final RelativeDateTimeParser dateTimeParser) {
        super(dateTimeParser);
    }

    @Override
    protected String expression() {
        return RelativeDateTimeExpression.UTC.alias();
    }

    @Override
    protected Object resolveValue(final LocalDateTime dateTime) {
        return DateTimeUtils.toOffsetDateTime(DateTimeUtils.toUtcLocalDateTime(dateTime))
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}

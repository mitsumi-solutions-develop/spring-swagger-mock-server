package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.resolvers.impl;

import io.github.mitsumi.solutions.shared.parsers.RelativeDateTimeParser;
import io.github.mitsumi.solutions.shared.utils.DateTimeUtils;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.enumerations.RelativeDateTimeExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@SuppressWarnings("PMD.CommentRequired")
public class JstEpochRelativeDateTimeResolver extends AbstractRelativeDateTimeResolver {

    public JstEpochRelativeDateTimeResolver(final RelativeDateTimeParser dateTimeParser) {
        super(dateTimeParser);
    }

    @Override
    protected String expression() {
        return RelativeDateTimeExpression.JST_EPOCH.alias();
    }

    @Override
    protected Object resolveValue(final LocalDateTime dateTime) {
        return DateTimeUtils.toJstOffsetDateTime(dateTime).toEpochSecond();
    }
}

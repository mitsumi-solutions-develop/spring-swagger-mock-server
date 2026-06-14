package io.github.mitsumi.solutions.spring.swagger.mock.server.app.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
@SuppressWarnings("PMD.CommentRequired")
public enum RelativeDateTimeExpression {

    JST("jst"),
    UTC("utc"),
    JST_EPOCH("epoch-jst"),
    UTC_EPOCH("epoch-utc");

    private final String alias;
}

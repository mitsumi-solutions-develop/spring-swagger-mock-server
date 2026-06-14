package io.github.mitsumi.solutions.spring.swagger.mock.server.app.enumerations;

import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@SuppressWarnings("PMD.CommentRequired")
public enum UUIDExpression {

    DEFAULT("[uuid]", UUID.randomUUID()::toString),

    UUID32("[uuid::32]", UUIDExpression::uuid32),

    UUID64("[uuid::64]", UUIDExpression::uuid64);

    private final String alias;
    private final Supplier<String> revolver;

    private static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static String uuid64() {
        return uuid32() + uuid32();
    }

    public static String resolve(final String content) {
        var temp = content;

        for (final var expression : values()) {
            temp = temp.contains(expression.alias) ?
                temp.replace(expression.alias, expression.revolver.get()) : temp;
        }

        return temp;
    }
}

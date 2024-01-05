package io.qifan.mall.server.infrastructure.jimmer;

import java.util.UUID;
import org.babyfish.jimmer.sql.meta.UserIdGenerator;

public class UUIDIdGenerator implements UserIdGenerator<String> {
    public UUIDIdGenerator() {
    }

    public String generate(Class<?> entityType) {
        return UUID.randomUUID().toString();
    }
}

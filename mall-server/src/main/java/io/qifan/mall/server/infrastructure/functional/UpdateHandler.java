package io.qifan.mall.server.infrastructure.functional;

import java.util.function.Consumer;


public interface UpdateHandler<T> {

    Executor<T> update(Consumer<T> consumer);

}

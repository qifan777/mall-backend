package io.qifan.mall.server.infrastructure.functional;

import java.util.function.Supplier;


public interface Create<T> {

    UpdateHandler<T> create(Supplier<T> supplier);

}

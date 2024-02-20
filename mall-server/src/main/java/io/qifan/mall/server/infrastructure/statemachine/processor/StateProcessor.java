package io.qifan.mall.server.infrastructure.statemachine.processor;


import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;

public interface StateProcessor<T, C> {

  R<T> action(StateContext<C> context);
}

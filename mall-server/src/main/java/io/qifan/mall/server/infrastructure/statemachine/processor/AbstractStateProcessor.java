package io.qifan.mall.server.infrastructure.statemachine.processor;

import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import lombok.SneakyThrows;

public abstract class AbstractStateProcessor<T, C> implements StateActionStep<T, C>,
    StateProcessor<T, C> {

  @SneakyThrows
  @Override
  public R<T> action(StateContext<C> context) {
    R<T> result = null;

    // 数据准备
    this.prepare(context);
    // 串行校验器
    result = this.check(context);
    if (!result.isSuccess()) {
      return result;
    }
    // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
    String nextState = this.getNextState(context);
    // 业务逻辑
    result = this.action(nextState, context);
    if (!result.isSuccess()) {
      return result;
    }
    // 持久化
    result = this.save(nextState, context);
    if (!result.isSuccess()) {
      return result;
    }
    // after
    this.after(context);
    return result;

  }
}

package io.qifan.mall.server.infrastructure.statemachine.processor;


import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;

public interface StateActionStep<T, C> {

  /**
   * 准备数据
   */
  default void prepare(StateContext<C> context) {
  }

  /**
   * 校验
   */
  R<T> check(StateContext<C> context);

  /**
   * 获取当前状态处理器处理完毕后，所处于的下一个状态
   */
  String getNextState(StateContext<C> context);

  /**
   * 状态动作方法，主要状态迁移逻辑
   */
  R<T> action(String nextState, StateContext<C> context);

  /**
   * 状态数据持久化
   */
  R<T> save(String nextState, StateContext<C> context);

  /**
   * 状态迁移成功，持久化后执行的后续处理
   */
  void after(StateContext<C> context);
}

package io.qifan.mall.server.infrastructure.statemachine.model;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
public class StateContext<C> {

  /**
   * 订单操作事件
   */
  private StateEvent stateEvent;

  /**
   * 业务可定义的上下文泛型对象
   */
  private C context;

  public StateContext(StateEvent stateEvent, C context) {
    this.stateEvent = stateEvent;
    this.context = context;
  }

  /**
   * 订单状态迁移事件
   */
  @Data
  @Accessors(chain = true)
  @Builder
  public static class StateEvent {

    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 触发事件
     */
    private String eventType;
    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 业务场景
     */
    private String sceneId;
  }
}

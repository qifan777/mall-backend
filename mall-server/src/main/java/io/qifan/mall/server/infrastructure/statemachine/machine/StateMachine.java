package io.qifan.mall.server.infrastructure.statemachine.machine;


import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext.StateEvent;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.StateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.registry.StateProcessorRegistry;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@Component
public class StateMachine {

  public <T, C> R<T> action(StateContext<C> context) {
    // 获取当前事件处理器
    StateProcessor<T, C> stateProcessor = this.getStateProcessor(context);
    // 执行处理逻辑
    return stateProcessor.action(context);
  }


  private <T, C> StateProcessor<T, C> getStateProcessor(StateContext<C> context) {
    StateEvent stateEvent = context.getStateEvent();
    // 根据状态+事件对象获取所对应的业务处理器集合
    List<AbstractStateProcessor> processorList =
        StateProcessorRegistry.acquireStateProcessor(stateEvent.getOrderState(),
            stateEvent.getEventType(), stateEvent.getBusinessCode(),
            stateEvent.getSceneId());
    if (processorList == null) {
      // 订单状态发生改变
      if (!Objects.isNull(stateEvent.getOrderState()) && !stateEvent.getOrderState()
          .equals(stateEvent.getOrderState())) {
        throw new BusinessException(ResultCode.TransferStatusError, "订单状态不匹配");
      }
      throw new BusinessException(ResultCode.NotFindError, "状态处理器未找到");
    }
    if (CollectionUtils.isEmpty(processorList)) {
      throw new BusinessException(ResultCode.NotFindError, "状态处理器未找到");
    }
    if (processorList.size() > 1) {
      throw new BusinessException(ResultCode.ValidateError, "状态处理器超过1");
    }
    return processorList.get(0);
  }
}
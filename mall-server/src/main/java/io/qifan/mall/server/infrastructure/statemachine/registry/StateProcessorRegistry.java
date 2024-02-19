package io.qifan.mall.server.infrastructure.statemachine.registry;

import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.StateProcessor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class StateProcessorRegistry implements BeanPostProcessor {

  /**
   * 第一层key是订单状态。 第二层key是订单状态对应的事件，一个状态可以有多个事件。 第三层key是具体场景code，场景下对应的多个处理器，需要后续进行过滤选择出一个具体的执行。
   */
  private static Map<String, Map<String, Map<String, List<AbstractStateProcessor>>>> stateProcessorMap =
      new ConcurrentHashMap<>();

  public static List<AbstractStateProcessor> acquireStateProcessor(String orderState,
      String eventType, String businessCode,
      String sceneId) {
    List<AbstractStateProcessor> abstractStateProcessors =
        stateProcessorMap.get(orderState).get(eventType).get(businessCode + "@" + sceneId);
    return abstractStateProcessors;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof AbstractStateProcessor && bean.getClass().isAnnotationPresent(
        OrderStateProcessor.class)) {
      OrderStateProcessor annotation = bean.getClass().getAnnotation(OrderStateProcessor.class);
      String[] states = annotation.state();
      String event = annotation.event();
      String[] bizCodes = annotation.bizCode();
      String[] sceneIds = annotation.sceneId();
      initProcessorMap(states, event, bizCodes, sceneIds, stateProcessorMap,
          (AbstractStateProcessor<Object, Object>) bean);
    }
    return bean;
  }

  private <E extends StateProcessor<T, C>, T, C> void initProcessorMap(String[] states, String event,
      String[] bizCodes,
      String[] sceneIds,
      Map<String, Map<String, Map<String, List<E>>>> map,
      E processor) {
    for (String bizCode : bizCodes) {
      for (String sceneId : sceneIds) {
        Arrays.asList(states).parallelStream().forEach(orderStateEnum -> {
          registerStateProcessor(orderStateEnum, event, bizCode, sceneId, map, processor);
        });
      }
    }
  }

  /**
   * 初始化状态机处理器
   */
  public <E extends StateProcessor> void registerStateProcessor(String orderStateEnum, String event,
      String bizCode,
      String sceneId,
      Map<String, Map<String, Map<String, List<E>>>> map,
      E processor) {
    // state维度
    if (!map.containsKey(orderStateEnum)) {
      map.put(orderStateEnum, new ConcurrentHashMap<>());
    }
    Map<String, Map<String, List<E>>> stateTransformEventEnumMap = map.get(orderStateEnum);
    // event维度
    if (!stateTransformEventEnumMap.containsKey(event)) {
      stateTransformEventEnumMap.put(event, new ConcurrentHashMap<>());
    }
    // bizCode and sceneId
    Map<String, List<E>> processorMap = stateTransformEventEnumMap.get(event);
    String bizCodeAndSceneId = bizCode + "@" + sceneId;
    if (!processorMap.containsKey(bizCodeAndSceneId)) {
      processorMap.put(bizCodeAndSceneId, new CopyOnWriteArrayList<>());
    }
    processorMap.get(bizCodeAndSceneId).add(processor);
  }
}

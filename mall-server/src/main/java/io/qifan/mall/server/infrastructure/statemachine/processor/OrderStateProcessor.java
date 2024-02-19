package io.qifan.mall.server.infrastructure.statemachine.processor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 订单状态处理器注解标识
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface OrderStateProcessor {

  /**
   * 指定状态
   */
  String[] state() default {};

  /**
   * 业务
   */
  String[] bizCode() default {"*"};

  /**
   * 场景
   */
  String[] sceneId() default {"*"};

  /**
   * 事件
   */
  String event();
}
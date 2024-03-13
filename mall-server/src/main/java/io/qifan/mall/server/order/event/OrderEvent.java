package io.qifan.mall.server.order.event;

import io.qifan.mall.server.order.entity.ProductOrder;
import lombok.Data;

@Data
public class OrderEvent {

  public record OrderSuccessEvent(ProductOrder productOrder) {

  }

}

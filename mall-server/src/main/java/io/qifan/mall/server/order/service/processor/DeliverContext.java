package io.qifan.mall.server.order.service.processor;

import io.qifan.mall.server.order.entity.ProductOrder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeliverContext {

  private String trackingNumber;
  private ProductOrder productOrder;
}

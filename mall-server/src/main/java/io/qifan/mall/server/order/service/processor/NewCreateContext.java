package io.qifan.mall.server.order.service.processor;

import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput;
import io.qifan.mall.server.payment.entity.Payment;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NewCreateContext {

  private ProductOrderInput productOrderInput;
  private Payment payment;
  private ProductOrder productOrder;
}
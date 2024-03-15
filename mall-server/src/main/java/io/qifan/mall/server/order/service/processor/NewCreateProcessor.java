package io.qifan.mall.server.order.service.processor;

import cn.hutool.core.util.IdUtil;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderDraft;
import io.qifan.mall.server.order.entity.ProductOrderItemDraft;
import io.qifan.mall.server.order.event.OrderEvent.OrderSuccessEvent;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.order.service.ProductOrderService;
import io.qifan.mall.server.payment.entity.PaymentDraft;
import io.qifan.mall.server.payment.entity.dto.PaymentPriceView;
import io.qifan.mall.server.product.sku.entity.ProductSkuTable;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@OrderStateProcessor(state = "TO_BE_CREATE", event = "CREATE", bizCode = "PRODUCT_ORDER")
@AllArgsConstructor
public class NewCreateProcessor extends AbstractStateProcessor<String, NewCreateContext> {

  private final ProductOrderRepository productOrderRepository;
  private final ProductSkuRepository productSkuRepository;
  private final ProductOrderService productOrderService;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void prepare(StateContext<NewCreateContext> context) {
    PaymentPriceView calculated = productOrderService.calculate(
        context.getContext().getProductOrderInput());
    context.getContext().setPayment(PaymentDraft.$.produce(
        context.getContext().getProductOrderInput().getPayment().toEntity(),
        draft -> draft
            .setId(IdUtil.fastSimpleUUID())
            .setCouponAmount(calculated.getCouponAmount())
            // 商品价格计算
            .setProductAmount(calculated.getProductAmount())
            // 运费计算
            .setDeliveryFee(calculated.getDeliveryFee())
            // VIP价格计算
            .setVipAmount(calculated.getVipAmount())
            // 实际支付价格计算
            .setPayAmount(calculated.getPayAmount())
    ));
  }

  @Override
  public R<String> check(StateContext<NewCreateContext> context) {
    ;
    return R.ok();
  }

  @Override
  public String getNextState(StateContext<NewCreateContext> context) {
    return ProductOrderStatus.TO_BE_PAID.getKeyEnName();
  }

  @Override
  public R<String> action(String nextState, StateContext<NewCreateContext> context) {
    context.getContext().getProductOrderInput().getItems().forEach(productOrderItem -> {
      // 扣减库存
      ProductSkuTable t = ProductSkuTable.$;
      productSkuRepository
          .sql()
          .createUpdate(t)
          .set(t.stock(), t.stock().minus(productOrderItem.getSkuCount()))
          .where(t.id().eq(productOrderItem.getProductSkuId()))
          .execute();
    });
    // 扣减优惠券等等
    return R.ok();
  }

  @Override
  public R<String> save(String nextState, StateContext<NewCreateContext> context) {
    String orderId = IdUtil.fastSimpleUUID();
    ProductOrder entity = ProductOrderDraft.$.produce(context.getContext().getProductOrderInput()
            .toEntity(),
        draft -> {
          // 设置订单项关联的订单id
          draft.setItems(draft.items().stream().map(item -> {
            return ProductOrderItemDraft.$.produce(item, productOrderItemDraft -> {
              productOrderItemDraft.setProductOrderId(orderId);
            });
          }).toList());
          // 设置订单的id和状态
          draft
              .setId(orderId)
              .setStatus(ProductOrderStatus.valueOf(nextState));
          // 设置支付详情
          draft.setPayment(context.getContext().getPayment());
        });
    ProductOrder save = productOrderRepository.save(entity);
    context.getContext().setProductOrder(save);
    return R.ok(save.id());
  }

  @Override
  public void after(StateContext<NewCreateContext> context) {
    eventPublisher.publishEvent(new OrderSuccessEvent(context.getContext().getProductOrder()));
  }
}
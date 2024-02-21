package io.qifan.mall.server.order.service.processor;

import cn.hutool.core.util.IdUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderDraft;
import io.qifan.mall.server.order.entity.ProductOrderItemDraft;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.payment.entity.PaymentDraft;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.entity.ProductSkuTable;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@OrderStateProcessor(state = "TO_BE_CREATE", event = "CREATE", bizCode = "PRODUCT_ORDER")
@AllArgsConstructor
public class NewCreateProcessor extends AbstractStateProcessor<String, NewCreateContext> {

  private final ProductOrderRepository productOrderRepository;
  private final ProductSkuRepository productSkuRepository;

  @Override
  public void prepare(StateContext<NewCreateContext> context) {
    context.getContext().setPayment(PaymentDraft.$.produce(
        context.getContext().getProductOrderInput().getPayment().toEntity(),
        draft -> draft
            .setId(IdUtil.fastSimpleUUID())
            .setCouponAmount(BigDecimal.ZERO)
            // 商品价格计算
            .setProductAmount(BigDecimal.valueOf(0.01))
            // 实际支付价格计算
            .setPayAmount(BigDecimal.valueOf(0.01))
            // 运费计算
            .setDeliveryFee(BigDecimal.ZERO)
            // VIP价格计算
            .setVipAmount(BigDecimal.ZERO)));
  }

  @Override
  public R<String> check(StateContext<NewCreateContext> context) {
    context.getContext().getProductOrderInput().getItems().forEach(item -> {
      ProductSku productSku = productSkuRepository
          .findById(item.getProductSkuId())
          .orElseThrow(() -> new BusinessException(
              ResultCode.NotFindError, "商品SKU不存在"));
      if (productSku.stock() - item.getSkuCount() <= 0) {
        throw new BusinessException(ResultCode.ValidateError, "商品库存不足");
      }
      // 优惠券校验
    });
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
    return R.ok(productOrderRepository.save(entity).id());
  }

  @Override
  public void after(StateContext<NewCreateContext> context) {

  }
}
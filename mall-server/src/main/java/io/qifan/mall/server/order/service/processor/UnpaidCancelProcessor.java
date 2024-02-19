package io.qifan.mall.server.order.service.processor;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderDraft;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.entity.ProductOrderItemFetcher;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.product.sku.entity.ProductSkuDraft;
import io.qifan.mall.server.product.sku.entity.ProductSkuFetcher;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@OrderStateProcessor(state = "TO_BE_PAID", event = "CANCEL")
@Service
@AllArgsConstructor
public class UnpaidCancelProcessor extends AbstractStateProcessor<String, ProductOrder> {

  private final ProductSkuRepository productSkuRepository;
  private final ProductOrderRepository productOrderRepository;

  @Override
  public R<String> check(StateContext<ProductOrder> context) {
    ProductOrder productOrder = productOrderRepository.findById(context.getContext().id(),
            ProductOrderFetcher.$.creator(true).items(
                ProductOrderItemFetcher.$
                    .skuCount().productSku(ProductSkuFetcher.$.allScalarFields())))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    context.setContext(productOrder);
    if (!StpUtil.getLoginIdAsString().equals(context.getContext().creator().id())) {
      throw new BusinessException(ResultCode.NotGrant, "无法取消他人订单");
    }
    return R.ok();
  }

  @Override
  public String getNextState(StateContext<ProductOrder> context) {
    return ProductOrderStatus.CLOSED.getKeyEnName();
  }

  @Override
  public R<String> action(String nextState, StateContext<ProductOrder> context) {
    context.getContext().items().forEach(productOrderItem -> {
      // 恢复库存
      productSkuRepository.save(ProductSkuDraft.$.produce(productOrderItem.productSku(), draft -> {
        draft.setStock(draft.stock() + productOrderItem.skuCount());
      }));
    });
    // 恢复优惠券等等
    return R.ok();
  }

  @Override
  public R<String> save(String nextState, StateContext<ProductOrder> context) {
    ProductOrder produce = ProductOrderDraft.$.produce(context.getContext(), draft -> {
      draft.setStatus(ProductOrderStatus.valueOf(nextState));
    });

    return R.ok(productOrderRepository.save(produce).id());
  }

  @Override
  public void after(StateContext<ProductOrder> context) {

  }
}

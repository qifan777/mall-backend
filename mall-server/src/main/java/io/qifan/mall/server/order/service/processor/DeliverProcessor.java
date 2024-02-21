package io.qifan.mall.server.order.service.processor;


import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderDraft;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@OrderStateProcessor(state = "TO_BE_DELIVERED", event = "DELIVER",bizCode = "PRODUCT_ORDER")
@AllArgsConstructor
@Slf4j
public class DeliverProcessor extends AbstractStateProcessor<Boolean, DeliverContext> {

  private final ProductOrderRepository productOrderRepository;

  @Override
  public R<Boolean> check(StateContext<DeliverContext> context) {

    return R.ok(true);
  }

  @Override
  public String getNextState(StateContext<DeliverContext> context) {
    return ProductOrderStatus.TO_BE_RECEIVED.getKeyEnName();
  }

  @Override
  public R<Boolean> action(String nextState, StateContext<DeliverContext> context) {
    return R.ok(true);
  }

  @Override
  public R<Boolean> save(String nextState, StateContext<DeliverContext> context) {
    ProductOrder produce = ProductOrderDraft.$.produce(context.getContext().getProductOrder(),
        draft -> {
          draft.setStatus(ProductOrderStatus.valueOf(nextState))
              .setTrackingNumber(context.getContext().getTrackingNumber());
        });
    productOrderRepository.save(produce);
    return R.ok(true);
  }

  @Override
  public void after(StateContext<DeliverContext> context) {

  }
}

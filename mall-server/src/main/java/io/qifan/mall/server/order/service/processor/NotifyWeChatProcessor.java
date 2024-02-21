package io.qifan.mall.server.order.service.processor;

import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result.DecryptNotifyResult;
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
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.payment.entity.PaymentFetcher;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@OrderStateProcessor(state = "TO_BE_PAID", event = "NOTIFY", sceneId = "WE_CHAT_PAY", bizCode = "PRODUCT_ORDER")
@Slf4j
@AllArgsConstructor
public class NotifyWeChatProcessor extends AbstractStateProcessor<String, NotifyWeChatContext> {

  private final ProductOrderRepository orderRepository;

  @Override
  public void prepare(StateContext<NotifyWeChatContext> context) {
    NotifyWeChatContext notifyWeChatContext = context.getContext();
    DecryptNotifyResult notifyResult = notifyWeChatContext.getDecryptNotifyResult();
    String outTradeNo = notifyResult.getOutTradeNo();
    ProductOrder productOrder = orderRepository.findById(outTradeNo,
        ProductOrderFetcher.$.allScalarFields().creator(true).payment(
            PaymentFetcher.$.allScalarFields())).orElseThrow(
        () -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    // 设置微信支付订单id
    ProductOrder producedOrder = ProductOrderDraft.$.produce(productOrder, draft -> {
      draft.payment().setTradeNo(notifyResult.getTransactionId())
          .setPayTime(LocalDateTime.now());
    });
    notifyWeChatContext.setProductOrder(producedOrder);
  }

  @Override
  public R<String> check(StateContext<NotifyWeChatContext> context) {
    // 可以校验支付的金额是否正确
    return R.ok();
  }

  @Override
  public String getNextState(StateContext<NotifyWeChatContext> context) {
    return ProductOrderStatus.TO_BE_DELIVERED.getKeyEnName();
  }

  @Override
  public R<String> action(String nextState, StateContext<NotifyWeChatContext> context) {
    // 后续拉新分成可以在这边发送事件
    return R.ok();
  }

  @Override
  public R<String> save(String nextState, StateContext<NotifyWeChatContext> context) {
    ProductOrder productOrder = context.getContext().getProductOrder();
    StpUtil.switchTo(productOrder.creator().id());
    ProductOrder producedOrder = ProductOrderDraft.$.produce(productOrder, draft -> {
      draft.setStatus(ProductOrderStatus.valueOf(nextState));
    });
    return R.ok(orderRepository.save(producedOrder).id());
  }

  @Override
  public void after(StateContext<NotifyWeChatContext> context) {

  }
}

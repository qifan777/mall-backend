package io.qifan.mall.server.order.service.processor;

import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request.Amount;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.dict.model.DictConstants.RefundStatus;
import io.qifan.mall.server.infrastructure.statemachine.machine.StateMachine;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.processor.AbstractStateProcessor;
import io.qifan.mall.server.infrastructure.statemachine.processor.OrderStateProcessor;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderDraft;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.refund.entity.RefundRecord;
import io.qifan.mall.server.refund.entity.RefundRecordDraft;
import io.qifan.mall.server.refund.repository.RefundRecordRepository;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@OrderStateProcessor(state = "TO_BE_DELIVERED", event = "REFUND", bizCode = "PRODUCT_ORDER")
@AllArgsConstructor
@Slf4j
public class PaidRefundWeChatProcessor extends
    AbstractStateProcessor<String, PaidRefundWeChatContext> {

  private final ProductOrderRepository productOrderRepository;
  private final RefundRecordRepository refundRecordRepository;
  private final WxPayService wxPayService;


  @Override
  public void prepare(StateContext<PaidRefundWeChatContext> context) {
    RefundRecord refundRecord = context.getContext().getRefundRecord();
    ProductOrder productOrder = productOrderRepository.findById(
            refundRecord
                .orderId(), ProductOrderFetcher.$.allScalarFields().creator(true))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    context.getContext().setProductOrder(productOrder);
    WxPayRefundV3Request wxPayRefundV3Request = new WxPayRefundV3Request().setOutTradeNo(
            refundRecord.orderId())
        .setOutRefundNo(refundRecord.id())
        .setReason(refundRecord.reason())
        .setAmount(new Amount().setRefund(refundRecord.amount().multiply(BigDecimal.valueOf(
                100)).intValue())
            .setTotal(refundRecord.originalAmount().multiply(BigDecimal.valueOf(100)).intValue())
            .setCurrency("CNY"));
    context.getContext().setPayRefundV3Request(wxPayRefundV3Request);
  }

  @Override
  public R<String> check(StateContext<PaidRefundWeChatContext> context) {
    if (!StpUtil.getLoginIdAsString()
        .equals(context.getContext().getProductOrder().creator().id())) {
      throw new BusinessException(ResultCode.NotGrant, "无法取消他人订单");
    }
    return R.ok();
  }

  @Override
  public String getNextState(StateContext<PaidRefundWeChatContext> context) {
    return ProductOrderStatus.TO_BE_PAID.getKeyEnName();
  }

  @Override
  public R<String> action(String nextState, StateContext<PaidRefundWeChatContext> context) {
    try {
      WxPayRefundV3Result wxPayRefundV3Result = wxPayService.refundV3(
          context.getContext().getPayRefundV3Request());
      context.getContext().setPayRefundV3Result(wxPayRefundV3Result);
    } catch (WxPayException e) {
      log.error("退款失败：", e);
      throw new BusinessException(ResultCode.Fail, "退款失败");
    }
    ;
    return R.ok();
  }

  @Override
  public R<String> save(String nextState, StateContext<PaidRefundWeChatContext> context) {
    refundRecordRepository.save(
        RefundRecordDraft.$.produce(context.getContext().getRefundRecord(), draft -> {
          draft.setStatus(RefundStatus.APPROVED)
              .setRefundId(context.getContext().getPayRefundV3Result().getRefundId());
        }));
    ProductOrder save = productOrderRepository.save(ProductOrderDraft.$.produce(context.getContext()
        .getProductOrder(), draft -> {
      draft.setStatus(ProductOrderStatus.valueOf(nextState));
    }));
    context.getContext().setProductOrder(save);
    return R.ok(save.id());
  }

  @Override
  public void after(StateContext<PaidRefundWeChatContext> context) {
  }
}

package io.qifan.mall.server.order.service;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result.DecryptNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result.JsapiResult;
import com.github.binarywang.wxpay.service.WxPayService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.service.CouponUserService;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.infrastructure.statemachine.machine.StateMachine;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext.StateEvent;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput.TargetOf_items;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.order.service.processor.DeliverContext;
import io.qifan.mall.server.order.service.processor.NewCreateContext;
import io.qifan.mall.server.order.service.processor.NotifyWeChatContext;
import io.qifan.mall.server.order.service.processor.PaidRefundWeChatContext;
import io.qifan.mall.server.order.service.processor.PrepayWeChatContext;
import io.qifan.mall.server.payment.entity.Payment;
import io.qifan.mall.server.payment.entity.PaymentDraft;
import io.qifan.mall.server.payment.entity.PaymentFetcher;
import io.qifan.mall.server.payment.entity.dto.PaymentPriceView;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import io.qifan.mall.server.refund.entity.RefundRecord;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductOrderService {

  private final ProductSkuRepository productSkuRepository;

  private final ProductOrderRepository productOrderRepository;
  private final StateMachine stateMachine;
  private final WxPayService wxPayService;
  private final CouponUserService couponUserService;

  public ProductOrder findById(String id) {
    return productOrderRepository.findById(id, ProductOrderRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(ProductOrderInput productOrderInput) {
    return productOrderRepository.save(productOrderInput).id();
  }

  public Page<ProductOrder> query(QueryRequest<ProductOrderSpec> queryRequest) {
    return productOrderRepository.findPage(queryRequest, ProductOrderRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    productOrderRepository.deleteAllById(ids);
    return true;
  }

  public String create(ProductOrderInput productOrderInput) {
    StateEvent stateEvent = StateEvent.builder()
        .orderState(ProductOrderStatus.TO_BE_CREATE.getKeyEnName())
        .eventType("CREATE")
        .sceneId("*")
        .businessCode("PRODUCT_ORDER").build();
    NewCreateContext newCreateContext = new NewCreateContext().setProductOrderInput(
        productOrderInput);
    R<String> res = stateMachine.action(new StateContext<>(stateEvent, newCreateContext));
    return res.getResult();
  }

  public JsapiResult prepay(String id) {
    ProductOrder productOrder = productOrderRepository
        .findById(id,
            ProductOrderFetcher.$
                .allScalarFields()
                .payment(PaymentFetcher.$.payType()))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    StateEvent stateEvent = StateEvent.builder()
        .orderState(productOrder.status().getKeyEnName())
        .eventType("PREPAY")
        .sceneId(productOrder.payment().payType().getKeyEnName())
        .businessCode("PRODUCT_ORDER")
        .build();
    R<JsapiResult> res = stateMachine.action(
        new StateContext<>(stateEvent, new PrepayWeChatContext()
            .setOrderId(id)));
    return res.getResult();
  }

  @SneakyThrows
  public String paymentNotifyWechat(String body, SignatureHeader signatureHeader) {
    DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(body, signatureHeader)
        .getResult();
    String outTradeNo = notifyResult.getOutTradeNo();
    ProductOrder productOrder = productOrderRepository
        .findById(outTradeNo,
            ProductOrderFetcher.$
                .allScalarFields()
                .payment(PaymentFetcher.$.payType()))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    StateEvent stateEvent = StateEvent.builder()
        .orderState(productOrder.status().getKeyEnName())
        .eventType("NOTIFY")
        .sceneId(productOrder.payment().payType().getKeyEnName())
        .businessCode("PRODUCT_ORDER")
        .build();
    R<String> res = stateMachine.action(
        new StateContext<>(stateEvent, new NotifyWeChatContext()
            .setDecryptNotifyResult(notifyResult)));
    return res.getResult();
  }

  public Boolean cancel(String id) {
    ProductOrder orderInfo = productOrderRepository.findById(id)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    StateEvent stateEvent = StateEvent.builder()
        .orderState(orderInfo.status().getKeyEnName())
        .eventType("CANCEL")
        .sceneId("*")
        .businessCode("PRODUCT_ORDER")
        .build();
    R<String> res = stateMachine.action(
        new StateContext<>(stateEvent, orderInfo));
    return res.isSuccess();
  }

  public Boolean deliver(String id, String trackingNumber) {
    ProductOrder orderInfo = productOrderRepository.findById(id)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    StateEvent stateEvent = StateEvent.builder()
        .orderState(orderInfo.status().getKeyEnName())
        .eventType("DELIVER")
        .sceneId("*")
        .businessCode("*")
        .build();
    DeliverContext deliverContext = new DeliverContext().setProductOrder(orderInfo)
        .setTrackingNumber(trackingNumber);
    R<Boolean> res = stateMachine.action(
        new StateContext<>(stateEvent, deliverContext));
    return res.getResult();
  }

  public Boolean refund(RefundRecord refundRecord) {
    ProductOrder orderInfo = productOrderRepository.findById(refundRecord.orderId(),
            ProductOrderFetcher.$.creator(true).payment(PaymentFetcher.$.payType()))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    StateEvent stateEvent = StateEvent.builder()
        .orderState(orderInfo.status().getKeyEnName())
        .eventType("REFUND")
        .sceneId(orderInfo.payment().payType().getKeyEnName())
        .businessCode("PRODUCT_ORDER")
        .build();
    PaidRefundWeChatContext paidRefundWeChatContext = new PaidRefundWeChatContext().setRefundRecord(
        refundRecord);
    stateMachine.action(
        new StateContext<>(stateEvent, paidRefundWeChatContext));
    cancel(refundRecord.orderId());
    return true;
  }

  public List<CouponUser> availableCoupons(BigDecimal price) {
    return couponUserService.availableCoupons(price);
  }

  public PaymentPriceView calculate(ProductOrderInput productOrderInput) {
    Payment produce = PaymentDraft.$.produce(draft -> {
      draft.setProductAmount(BigDecimal.ZERO)
          .setVipAmount(BigDecimal.ZERO)
          .setDeliveryFee(BigDecimal.ZERO)
          .setCouponAmount(BigDecimal.ZERO);

      BigDecimal totalPrice = BigDecimal.ZERO;
      for (TargetOf_items item : productOrderInput.getItems()) {
        ProductSku productSku = productSkuRepository.findById(item.getProductSkuId())
            .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "商品不存在"));
        if (productSku.stock() - item.getSkuCount() <= 0) {
          throw new BusinessException(ResultCode.ValidateError, "商品库存不足");
        }
        BigDecimal price = productSku.price().multiply(BigDecimal.valueOf(item.getSkuCount()));
        totalPrice = totalPrice.add(price);
      }
      // 计算商品总价
      draft.setProductAmount(totalPrice);
      if (productOrderInput.getCouponUser() != null && StringUtils.hasText(
          productOrderInput.getCouponUser().getId())) {
        // 计算优惠券优化价格
        draft.setCouponAmount(
            couponUserService.calculate(productOrderInput.getCouponUser().getId(), totalPrice));
      }
      // 计算运费
      // 计算VIP优惠价格
      // 计算实际支付价格
      draft.setPayAmount(
          draft.productAmount().add(draft.deliveryFee()).subtract(draft.couponAmount())
              .subtract(draft.vipAmount())
      );

    });
    return PaymentPriceView.of(produce);
  }
}
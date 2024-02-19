package io.qifan.mall.server.order.service;

import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result.DecryptNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result.JsapiResult;
import com.github.binarywang.wxpay.service.WxPayService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.infrastructure.statemachine.machine.StateMachine;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext;
import io.qifan.mall.server.infrastructure.statemachine.model.StateContext.StateEvent;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.service.processor.NewCreateContext;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.order.service.processor.NotifyWeChatContext;
import io.qifan.mall.server.order.service.processor.PrepayWeChatContext;
import io.qifan.mall.server.payment.entity.PaymentFetcher;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductOrderService {

  private final ProductOrderRepository productOrderRepository;
  private final StateMachine stateMachine;
  private final WxPayService wxPayService;

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
        .businessCode("*").build();
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
        .businessCode("*")
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
        .businessCode("*")
        .build();
    R<String> res = stateMachine.action(
        new StateContext<>(stateEvent, new NotifyWeChatContext()
            .setDecryptNotifyResult(notifyResult)));
    return res.getResult();
  }
}
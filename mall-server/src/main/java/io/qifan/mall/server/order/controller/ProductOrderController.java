package io.qifan.mall.server.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec.TargetOf_creator;
import io.qifan.mall.server.order.repository.ProductOrderRepository;
import io.qifan.mall.server.order.service.ProductOrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.ApiIgnore;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("productOrder")
@AllArgsConstructor
@DefaultFetcherOwner(ProductOrderRepository.class)
public class ProductOrderController {

  private final ProductOrderService productOrderService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") ProductOrder findById(@PathVariable String id) {
    return productOrderService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") ProductOrder> query(
      @RequestBody QueryRequest<ProductOrderSpec> queryRequest) {
    return productOrderService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated ProductOrderInput productOrder) {
    return productOrderService.save(productOrder);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return productOrderService.delete(ids);
  }

  @PostMapping("create")
  public String create(@RequestBody @Validated ProductOrderInput productOrder) {
    return productOrderService.create(productOrder);
  }

  @PostMapping("user")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") ProductOrder> queryByUser(
      @RequestBody QueryRequest<ProductOrderSpec> queryRequest) {
    TargetOf_creator targetOfCreator = new TargetOf_creator();
    targetOfCreator.setId(StpUtil.getLoginIdAsString());
    queryRequest.getQuery().setCreator(targetOfCreator);
    return productOrderService.query(queryRequest);
  }

  @PostMapping("{id}/prepay")
  public WxPayUnifiedOrderV3Result.JsapiResult prepay(@PathVariable String id) {
    return productOrderService.prepay(id);
  }

  @PostMapping("notify/wechat")
  @ApiIgnore
  public String paymentNotifyWechat(@RequestBody String body,
      @RequestHeader(value = "Wechatpay-Timestamp") String timestamp,
      @RequestHeader(value = "Wechatpay-Nonce") String nonce,
      @RequestHeader(value = "Wechatpay-Signature") String signature,
      @RequestHeader(value = "Wechatpay-Serial") String serial) {
    SignatureHeader signatureHeader = SignatureHeader.builder().signature(signature)
        .serial(serial)
        .nonce(nonce)
        .timeStamp(timestamp).build();
    return productOrderService.paymentNotifyWechat(body, signatureHeader);
  }

  @PostMapping("{id}/cancel")
  public Boolean cancel(@PathVariable String id) {
    return productOrderService.cancel(id);
  }

  @PostMapping("{id}/deliver")
  public Boolean deliver(@PathVariable String id, @RequestParam String trackingNumber) {
//    TODO
//    alter table product_order
//    add tracking_number varchar(50);
    return productOrderService.deliver(id, trackingNumber);
  }
}
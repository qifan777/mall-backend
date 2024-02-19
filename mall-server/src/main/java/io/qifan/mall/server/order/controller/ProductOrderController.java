package io.qifan.mall.server.order.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.EnableDtoGeneration;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
package io.qifan.mall.server.order.product.root.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.product.root.entity.ProductOrder;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderCalculateInput;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderInput;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderPriceView;
import io.qifan.mall.server.order.product.root.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.order.product.root.repository.ProductOrderRepository;
import io.qifan.mall.server.order.product.root.service.ProductOrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("productOrder")
@AllArgsConstructor
public class ProductOrderController {

  private final ProductOrderService productOrderService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductOrderRepository.class) ProductOrder findById(
      @PathVariable String id) {
    return productOrderService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductOrderRepository.class) ProductOrder> query(
      @RequestBody QueryRequest<ProductOrderSpec> queryRequest) {
    return productOrderService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Valid ProductOrderInput productOrder) {
    return productOrderService.save(productOrder);
  }

  @PostMapping("delete")
  public boolean delete(@RequestBody List<String> ids) {
    return productOrderService.delete(ids);
  }

  @PostMapping("calculate")
  public ProductOrderPriceView orderCalculate(
      @Valid @RequestBody ProductOrderCalculateInput calculateInput) {
    return productOrderService.calculate(calculateInput);
  }
}
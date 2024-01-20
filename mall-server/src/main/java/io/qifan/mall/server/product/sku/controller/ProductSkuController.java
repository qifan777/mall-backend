package io.qifan.mall.server.product.sku.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuInput;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuSpec;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import io.qifan.mall.server.product.sku.service.ProductSkuService;
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
@RequestMapping("productSku")
@AllArgsConstructor
@DefaultFetcherOwner(ProductSkuRepository.class)
public class ProductSkuController {

  private final ProductSkuService productSkuService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") ProductSku findById(@PathVariable String id) {
    return productSkuService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") ProductSku> query(
      @RequestBody QueryRequest<ProductSkuSpec> queryRequest) {
    return productSkuService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated ProductSkuInput productSku) {
    return productSkuService.save(productSku);
  }
  @PostMapping("save-batch")
  public Boolean saveBatch(@RequestBody List<ProductSkuInput> productSkus) {
    productSkus.forEach(productSkuService::save);
    return true;
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return productSkuService.delete(ids);
  }
}
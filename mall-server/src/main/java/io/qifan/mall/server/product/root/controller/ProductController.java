package io.qifan.mall.server.product.root.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.root.entity.Product;
import io.qifan.mall.server.product.root.entity.dto.ProductInput;
import io.qifan.mall.server.product.root.entity.dto.ProductSpec;
import io.qifan.mall.server.product.root.repository.ProductRepository;
import io.qifan.mall.server.product.root.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
@AllArgsConstructor
@DefaultFetcherOwner(ProductRepository.class)
public class ProductController {

  private final ProductService productService;

  @GetMapping("{id}")
  public @FetchBy(value = "PRODUCT_SKU_FETCHER") Product findById(@PathVariable String id) {
    return productService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") Product> query(
      @RequestBody QueryRequest<ProductSpec> queryRequest) {
    return productService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated ProductInput product) {
    return productService.save(product);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return productService.delete(ids);
  }
}
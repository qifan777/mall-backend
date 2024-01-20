package io.qifan.mall.server.product.category.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.category.entity.ProductCategory;
import io.qifan.mall.server.product.category.entity.dto.ProductCategoryInput;
import io.qifan.mall.server.product.category.entity.dto.ProductCategorySpec;
import io.qifan.mall.server.product.category.repository.ProductCategoryRepository;
import io.qifan.mall.server.product.category.service.ProductCategoryService;
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
@RequestMapping("productCategory")
@AllArgsConstructor
@DefaultFetcherOwner(ProductCategoryRepository.class)
public class ProductCategoryController {

  private final ProductCategoryService productCategoryService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") ProductCategory findById(@PathVariable String id) {
    return productCategoryService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") ProductCategory> query(
      @RequestBody QueryRequest<ProductCategorySpec> queryRequest) {
    return productCategoryService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated ProductCategoryInput productCategory) {
    return productCategoryService.save(productCategory);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return productCategoryService.delete(ids);
  }
}
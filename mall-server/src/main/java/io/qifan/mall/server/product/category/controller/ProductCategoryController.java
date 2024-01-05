package io.qifan.mall.server.product.category.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.category.entity.ProductCategory;
import io.qifan.mall.server.product.category.entity.dto.ProductCategoryInput;
import io.qifan.mall.server.product.category.entity.dto.ProductCategorySpec;
import io.qifan.mall.server.product.category.repository.ProductCategoryRepository;
import io.qifan.mall.server.product.category.service.ProductCategoryService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productCategory")
@AllArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductCategoryRepository.class) ProductCategory findById(
            @PathVariable String id) {
        return productCategoryService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductCategoryRepository.class) ProductCategory> query(
            @RequestBody QueryRequest<ProductCategorySpec> queryRequest) {
        return productCategoryService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductCategoryInput productCategory) {
        return productCategoryService.save(productCategory);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return productCategoryService.delete(ids);
    }
}
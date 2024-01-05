package io.qifan.mall.server.product.root.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.root.entity.Product;
import io.qifan.mall.server.product.root.entity.dto.ProductInput;
import io.qifan.mall.server.product.root.entity.dto.ProductSpec;
import io.qifan.mall.server.product.root.repository.ProductRepository;
import io.qifan.mall.server.product.root.service.ProductService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductRepository.class) Product findById(
            @PathVariable String id) {
        return productService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductRepository.class) Product> query(
            @RequestBody QueryRequest<ProductSpec> queryRequest) {
        return productService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductInput product) {
        return productService.save(product);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return productService.delete(ids);
    }
}
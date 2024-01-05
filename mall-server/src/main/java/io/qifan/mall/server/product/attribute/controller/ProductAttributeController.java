package io.qifan.mall.server.product.attribute.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.attribute.entity.ProductAttribute;
import io.qifan.mall.server.product.attribute.entity.dto.ProductAttributeInput;
import io.qifan.mall.server.product.attribute.entity.dto.ProductAttributeSpec;
import io.qifan.mall.server.product.attribute.repository.ProductAttributeRepository;
import io.qifan.mall.server.product.attribute.service.ProductAttributeService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productAttribute")
@AllArgsConstructor
public class ProductAttributeController {
    private final ProductAttributeService productAttributeService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductAttributeRepository.class) ProductAttribute findById(@PathVariable String id) {
        return productAttributeService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductAttributeRepository.class) ProductAttribute> query(@RequestBody QueryRequest<ProductAttributeSpec> queryRequest) {
        return productAttributeService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductAttributeInput productAttribute) {
        return productAttributeService.save(productAttribute);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return productAttributeService.delete(ids);
    }
}
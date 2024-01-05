package io.qifan.mall.server.product.sku.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuInput;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuSpec;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import io.qifan.mall.server.product.sku.service.ProductSkuService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productSku")
@AllArgsConstructor
public class ProductSkuController {
    private final ProductSkuService productSkuService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductSkuRepository.class) ProductSku findById(@PathVariable String id) {
        return productSkuService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = ProductSkuRepository.class) ProductSku> query(@RequestBody QueryRequest<ProductSkuSpec> queryRequest) {
        return productSkuService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductSkuInput productSku) {
        return productSkuService.save(productSku);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return productSkuService.delete(ids);
    }
}
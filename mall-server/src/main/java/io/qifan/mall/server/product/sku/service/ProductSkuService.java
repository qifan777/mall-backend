package io.qifan.mall.server.product.sku.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuInput;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuSpec;
import io.qifan.mall.server.product.sku.repository.ProductSkuRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductSkuService {
    private final ProductSkuRepository productSkuRepository;

    public ProductSku findById(String id) {
        return productSkuRepository.findById(id, ProductSkuRepository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(ProductSkuInput productSkuInput) {
        return productSkuRepository.save(productSkuInput).id();
    }

    public Page<ProductSku> query(QueryRequest<ProductSkuSpec> queryRequest) {
        return productSkuRepository.findPage(queryRequest, ProductSkuRepository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        productSkuRepository.deleteAllById(ids);
        return true;
    }
}
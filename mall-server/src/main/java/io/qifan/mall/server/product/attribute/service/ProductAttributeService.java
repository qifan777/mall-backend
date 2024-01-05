package io.qifan.mall.server.product.attribute.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.attribute.entity.ProductAttribute;
import io.qifan.mall.server.product.attribute.entity.dto.ProductAttributeInput;
import io.qifan.mall.server.product.attribute.entity.dto.ProductAttributeSpec;
import io.qifan.mall.server.product.attribute.repository.ProductAttributeRepository;
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
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttribute findById(String id) {
        return productAttributeRepository.findById(id, ProductAttributeRepository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(ProductAttributeInput productAttributeInput) {
        return productAttributeRepository.save(productAttributeInput).id();
    }

    public Page<ProductAttribute> query(QueryRequest<ProductAttributeSpec> queryRequest) {
        return productAttributeRepository.findPage(queryRequest, ProductAttributeRepository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        productAttributeRepository.deleteAllById(ids);
        return true;
    }
}
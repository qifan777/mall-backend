package io.qifan.mall.server.product.category.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.category.entity.ProductCategory;
import io.qifan.mall.server.product.category.entity.dto.ProductCategoryInput;
import io.qifan.mall.server.product.category.entity.dto.ProductCategorySpec;
import io.qifan.mall.server.product.category.repository.ProductCategoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;

  public ProductCategory findById(String id) {
    return productCategoryRepository.findById(id, ProductCategoryRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(ProductCategoryInput productCategoryInput) {
    return productCategoryRepository.save(productCategoryInput).id();
  }

  public Page<ProductCategory> query(QueryRequest<ProductCategorySpec> queryRequest) {
    return productCategoryRepository.findPage(queryRequest,
        ProductCategoryRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    productCategoryRepository.deleteAllById(ids);
    return true;
  }
}
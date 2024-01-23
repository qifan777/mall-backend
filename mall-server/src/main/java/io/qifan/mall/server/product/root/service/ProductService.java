package io.qifan.mall.server.product.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.root.entity.Product;
import io.qifan.mall.server.product.root.entity.dto.ProductInput;
import io.qifan.mall.server.product.root.entity.dto.ProductSpec;
import io.qifan.mall.server.product.root.repository.ProductRepository;
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
public class ProductService {

  private final ProductRepository productRepository;

  public Product findById(String id) {
    return productRepository.findById(id, ProductRepository.PRODUCT_SKU_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(ProductInput productInput) {
    return productRepository.save(productInput).id();
  }

  public Page<Product> query(QueryRequest<ProductSpec> queryRequest) {
    return productRepository.findPage(queryRequest, ProductRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    productRepository.deleteAllById(ids);
    return true;
  }
}
package io.qifan.mall.server.product.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.attribute.entity.ProductAttributeDraft;
import io.qifan.mall.server.product.root.entity.Product;
import io.qifan.mall.server.product.root.entity.ProductDraft;
import io.qifan.mall.server.product.root.entity.dto.ProductInput;
import io.qifan.mall.server.product.root.entity.dto.ProductSpec;
import io.qifan.mall.server.product.root.repository.ProductRepository;
import io.qifan.mall.server.product.sku.entity.ProductSkuDraft;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductService {

  private final ProductRepository productRepository;

  public Product findById(String id) {
    return productRepository.findById(id, ProductRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(ProductInput productInput) {
    Product entity = productInput.toEntity();
    String productId = StringUtils.hasText(productInput.getId()) ? productInput.getId()
        : UUID.randomUUID().toString();
    Product product = ProductDraft.$.produce(entity, draft -> {
      draft.setId(productId);
      draft.setAttributes(
          draft.attributes()
              .stream()
              .map(productAttribute ->
                  ProductAttributeDraft.$.produce(productAttribute,
                      productAttributeDraft -> productAttributeDraft
                          .applyProduct(p -> p.setId(productId))))
              .collect(Collectors.toList())
      );
      draft.setSkuList(
          draft.skuList()
              .stream()
              .map(productSku -> ProductSkuDraft.$.produce(productSku,
                  productSkuDraft -> productSkuDraft
                      .applyProduct(p -> p.setId(productId))))
              .collect(Collectors.toList()));
    });
    return productRepository.save(product).id();
  }

  public Page<Product> query(QueryRequest<ProductSpec> queryRequest) {
    return productRepository.findPage(queryRequest, ProductRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    productRepository.deleteAllById(ids);
    return true;
  }
}
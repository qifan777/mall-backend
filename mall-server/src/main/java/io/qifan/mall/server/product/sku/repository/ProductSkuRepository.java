package io.qifan.mall.server.product.sku.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import io.qifan.mall.server.product.sku.entity.ProductSkuFetcher;
import io.qifan.mall.server.product.sku.entity.ProductSkuTable;
import io.qifan.mall.server.product.sku.entity.dto.ProductSkuSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.ast.Predicate;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public interface ProductSkuRepository extends JRepository<ProductSku, String> {

  ProductSkuTable productSkuTable = ProductSkuTable.$;
  ProductSkuFetcher COMPLEX_FETCHER = ProductSkuFetcher.$.allScalarFields()
      .productId()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<ProductSku> findPage(QueryRequest<ProductSkuSpec> queryRequest,
      Fetcher<ProductSku> fetcher) {
    ProductSkuSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(productSkuTable)
        .where(query)
        .whereIf(StringUtils.hasText(query.getValuesStr()), Predicate.sql("%e like %v", it -> {
          it.expression(productSkuTable.values())
              .value("%" + query.getValuesStr() + "%");
        }))
        .orderBy(SpringOrders.toOrders(productSkuTable, pageable.getSort()))
        .select(productSkuTable.fetch(fetcher)));
  }
}
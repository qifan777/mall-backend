package io.qifan.mall.server.product.root.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.root.entity.Product;
import io.qifan.mall.server.product.root.entity.ProductFetcher;
import io.qifan.mall.server.product.root.entity.ProductTable;
import io.qifan.mall.server.product.root.entity.dto.ProductSpec;
import io.qifan.mall.server.product.sku.entity.ProductSkuFetcher;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends JRepository<Product, String> {

  ProductTable productTable = ProductTable.$;
  ProductFetcher COMPLEX_FETCHER = ProductFetcher.$.allScalarFields()
      .categoryId()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  ProductFetcher PRODUCT_SKU_FETCHER = ProductFetcher.$.allScalarFields()
      .categoryId()
      .skuList(ProductSkuFetcher.$.allScalarFields())
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Product> findPage(QueryRequest<ProductSpec> queryRequest, Fetcher<Product> fetcher) {
    ProductSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(productTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(productTable, pageable.getSort()))
        .select(productTable.fetch(fetcher)));
  }
}
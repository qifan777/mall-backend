package io.qifan.mall.server.order.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.entity.ProductOrderFetcher;
import io.qifan.mall.server.order.entity.ProductOrderTable;
import io.qifan.mall.server.order.entity.dto.ProductOrderSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductOrderRepository extends JRepository<ProductOrder, String> {

  ProductOrderTable productOrderTable = ProductOrderTable.$;
  ProductOrderFetcher COMPLEX_FETCHER = ProductOrderFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<ProductOrder> findPage(QueryRequest<ProductOrderSpec> queryRequest,
      Fetcher<ProductOrder> fetcher) {
    ProductOrderSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(productOrderTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(productOrderTable, pageable.getSort()))
        .select(productOrderTable.fetch(fetcher)));
  }
}
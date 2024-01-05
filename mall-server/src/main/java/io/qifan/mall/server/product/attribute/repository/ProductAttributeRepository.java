package io.qifan.mall.server.product.attribute.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.attribute.entity.ProductAttribute;
import io.qifan.mall.server.product.attribute.entity.ProductAttributeFetcher;
import io.qifan.mall.server.product.attribute.entity.ProductAttributeTable;
import io.qifan.mall.server.product.attribute.entity.dto.ProductAttributeSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductAttributeRepository extends JRepository<ProductAttribute, String> {
    ProductAttributeTable productAttributeTable = ProductAttributeTable.$;
    ProductAttributeFetcher COMPLEX_FETCHER = ProductAttributeFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());

    default Page<ProductAttribute> findPage(QueryRequest<ProductAttributeSpec> queryRequest, Fetcher<ProductAttribute> fetcher) {
        ProductAttributeSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return pager(pageable).execute(sql().createQuery(productAttributeTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(productAttributeTable, pageable.getSort()))
                .select(productAttributeTable.fetch(fetcher)));
    }
}
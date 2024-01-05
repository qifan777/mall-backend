package io.qifan.mall.server.product.category.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.product.category.entity.ProductCategory;
import io.qifan.mall.server.product.category.entity.ProductCategoryFetcher;
import io.qifan.mall.server.product.category.entity.ProductCategoryTable;
import io.qifan.mall.server.product.category.entity.dto.ProductCategorySpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryRepository extends JRepository<ProductCategory, String> {

    ProductCategoryTable productCategoryTable = ProductCategoryTable.$;
    ProductCategoryFetcher COMPLEX_FETCHER = ProductCategoryFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());

    default Page<ProductCategory> findPage(QueryRequest<ProductCategorySpec> queryRequest,
                                           Fetcher<ProductCategory> fetcher) {
        ProductCategorySpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return pager(pageable).execute(sql().createQuery(productCategoryTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(productCategoryTable, pageable.getSort()))
                .select(productCategoryTable.fetch(fetcher)));
    }
}
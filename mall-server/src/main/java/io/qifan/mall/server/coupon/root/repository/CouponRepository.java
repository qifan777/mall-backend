package io.qifan.mall.server.coupon.root.repository;

import io.qifan.mall.server.coupon.root.entity.Coupon;
import io.qifan.mall.server.coupon.root.entity.CouponFetcher;
import io.qifan.mall.server.coupon.root.entity.CouponTable;
import io.qifan.mall.server.coupon.root.entity.dto.CouponSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepository extends JRepository<Coupon, String> {

  CouponTable couponTable = CouponTable.$;
  CouponFetcher COMPLEX_FETCHER = CouponFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Coupon> findPage(QueryRequest<CouponSpec> queryRequest, Fetcher<Coupon> fetcher) {
    CouponSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(couponTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(couponTable, pageable.getSort()))
        .select(couponTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
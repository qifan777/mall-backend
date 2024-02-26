package io.qifan.mall.server.coupon.user.repository;

import io.qifan.mall.server.coupon.root.entity.CouponFetcher;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.entity.CouponUserFetcher;
import io.qifan.mall.server.coupon.user.entity.CouponUserTable;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponUserRepository extends JRepository<CouponUser, String> {

  CouponUserTable couponUserTable = CouponUserTable.$;
  CouponUserFetcher COMPLEX_FETCHER = CouponUserFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname())
      .coupon(CouponFetcher.$.allScalarFields())
      .user(UserFetcher.$.nickname().phone())
      .couponId()
      .userId();

  default Page<CouponUser> findPage(QueryRequest<CouponUserSpec> queryRequest,
      Fetcher<CouponUser> fetcher) {
    CouponUserSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(couponUserTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(couponUserTable, pageable.getSort()))
        .select(couponUserTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
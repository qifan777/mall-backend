package io.qifan.mall.server.coupon.use.repository;

import io.qifan.mall.server.coupon.use.entity.CouponUseRecord;
import io.qifan.mall.server.coupon.use.entity.CouponUseRecordFetcher;
import io.qifan.mall.server.coupon.use.entity.CouponUseRecordTable;
import io.qifan.mall.server.coupon.use.entity.dto.CouponUseRecordSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponUseRecordRepository extends JRepository<CouponUseRecord, String> {

  CouponUseRecordTable couponUseRecordTable = CouponUseRecordTable.$;
  CouponUseRecordFetcher COMPLEX_FETCHER = CouponUseRecordFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<CouponUseRecord> findPage(QueryRequest<CouponUseRecordSpec> queryRequest,
      Fetcher<CouponUseRecord> fetcher) {
    CouponUseRecordSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(couponUseRecordTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(couponUseRecordTable, pageable.getSort()))
        .select(couponUseRecordTable.fetch(fetcher)));
  }
}
package io.qifan.mall.server.refund.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.refund.entity.RefundRecord;
import io.qifan.mall.server.refund.entity.RefundRecordFetcher;
import io.qifan.mall.server.refund.entity.RefundRecordTable;
import io.qifan.mall.server.refund.entity.dto.RefundRecordSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RefundRecordRepository extends JRepository<RefundRecord, String> {

  RefundRecordTable refundRecordTable = RefundRecordTable.$;
  RefundRecordFetcher COMPLEX_FETCHER = RefundRecordFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<RefundRecord> findPage(QueryRequest<RefundRecordSpec> queryRequest,
      Fetcher<RefundRecord> fetcher) {
    RefundRecordSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(refundRecordTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(refundRecordTable, pageable.getSort()))
        .select(refundRecordTable.fetch(fetcher)));
  }
}
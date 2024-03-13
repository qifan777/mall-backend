package io.qifan.mall.server.wallet.record.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import io.qifan.mall.server.wallet.record.entity.WalletRecordFetcher;
import io.qifan.mall.server.wallet.record.entity.WalletRecordTable;
import io.qifan.mall.server.wallet.record.entity.dto.WalletRecordSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletRecordRepository extends JRepository<WalletRecord, String> {

  WalletRecordTable walletRecordTable = WalletRecordTable.$;
  WalletRecordFetcher COMPLEX_FETCHER = WalletRecordFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<WalletRecord> findPage(QueryRequest<WalletRecordSpec> queryRequest,
      Fetcher<WalletRecord> fetcher) {
    WalletRecordSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(walletRecordTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(walletRecordTable, pageable.getSort()))
        .select(walletRecordTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
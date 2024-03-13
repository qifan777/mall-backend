package io.qifan.mall.server.wallet.root.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.wallet.root.entity.Wallet;
import io.qifan.mall.server.wallet.root.entity.WalletFetcher;
import io.qifan.mall.server.wallet.root.entity.WalletTable;
import io.qifan.mall.server.wallet.root.entity.dto.WalletSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletRepository extends JRepository<Wallet, String> {

  WalletTable walletTable = WalletTable.$;
  WalletFetcher COMPLEX_FETCHER = WalletFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Wallet> findPage(QueryRequest<WalletSpec> queryRequest,
      Fetcher<Wallet> fetcher) {
    WalletSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(walletTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(walletTable, pageable.getSort()))
        .select(walletTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
package io.qifan.mall.server.dict.repository;

import io.qifan.mall.server.dict.entity.Dict;
import io.qifan.mall.server.dict.entity.DictFetcher;
import io.qifan.mall.server.dict.entity.DictTable;
import io.qifan.mall.server.dict.entity.dto.DictSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictRepository extends JRepository<Dict, String> {

  DictTable dictTable = DictTable.$;
  DictFetcher COMPLEX_FETCHER = DictFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Dict> findPage(QueryRequest<DictSpec> queryRequest, Fetcher<Dict> fetcher) {
    DictSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(dictTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(dictTable, pageable.getSort()))
        .select(dictTable.fetch(fetcher)));
  }
}
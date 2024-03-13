package io.qifan.mall.server.agent.level.repository;

import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.AgentLevelFetcher;
import io.qifan.mall.server.agent.level.entity.AgentLevelTable;
import io.qifan.mall.server.agent.level.entity.dto.AgentLevelSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentLevelRepository extends JRepository<AgentLevel, String> {

  AgentLevelTable agentLevelTable = AgentLevelTable.$;
  AgentLevelFetcher COMPLEX_FETCHER = AgentLevelFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<AgentLevel> findPage(QueryRequest<AgentLevelSpec> queryRequest,
      Fetcher<AgentLevel> fetcher) {
    AgentLevelSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(agentLevelTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(agentLevelTable, pageable.getSort()))
        .select(agentLevelTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
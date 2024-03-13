package io.qifan.mall.server.agent.root.repository;

import io.qifan.mall.server.agent.level.entity.AgentLevelFetcher;
import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.AgentFetcher;
import io.qifan.mall.server.agent.root.entity.AgentTable;
import io.qifan.mall.server.agent.root.entity.dto.AgentSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentRepository extends JRepository<Agent, String> {

  AgentTable agentTable = AgentTable.$;
  AgentFetcher COMPLEX_FETCHER = AgentFetcher.$.allScalarFields()
      .level(AgentLevelFetcher.$.allScalarFields())
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Agent> findPage(QueryRequest<AgentSpec> queryRequest,
      Fetcher<Agent> fetcher) {
    AgentSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(agentTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(agentTable, pageable.getSort()))
        .select(agentTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}
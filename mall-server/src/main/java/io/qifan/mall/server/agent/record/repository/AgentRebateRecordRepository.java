package io.qifan.mall.server.agent.record.repository;

import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecordFetcher;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecordTable;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec;
import io.qifan.mall.server.agent.root.entity.AgentFetcher;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.wallet.record.entity.WalletRecordFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentRebateRecordRepository extends JRepository<AgentRebateRecord, String> {

    AgentRebateRecordTable agentRebateRecordTable = AgentRebateRecordTable.$;
    AgentRebateRecordFetcher COMPLEX_FETCHER = AgentRebateRecordFetcher.$.allTableFields()
            .agent(AgentFetcher.$.user(UserFetcher.$.nickname().avatar()).agentNo())
            .walletRecord(WalletRecordFetcher.$.allScalarFields())
            .fromAgent(AgentFetcher.$.user(UserFetcher.$.nickname().avatar()).agentNo())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());

    default Page<AgentRebateRecord> findPage(QueryRequest<AgentRebateRecordSpec> queryRequest,
                                             Fetcher<AgentRebateRecord> fetcher) {
        AgentRebateRecordSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(agentRebateRecordTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(agentRebateRecordTable, pageable.getSort()))
                .select(agentRebateRecordTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}
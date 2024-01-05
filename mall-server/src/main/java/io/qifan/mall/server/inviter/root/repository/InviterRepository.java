package io.qifan.mall.server.inviter.root.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.root.entity.Inviter;
import io.qifan.mall.server.inviter.root.entity.InviterFetcher;
import io.qifan.mall.server.inviter.root.entity.InviterTable;
import io.qifan.mall.server.inviter.root.entity.dto.InviterSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InviterRepository extends JRepository<Inviter, String> {
    InviterTable inviterTable = InviterTable.$;
    InviterFetcher COMPLEX_FETCHER = InviterFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());

    default Page<Inviter> findPage(QueryRequest<InviterSpec> queryRequest, Fetcher<Inviter> fetcher) {
        InviterSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return pager(pageable).execute(sql().createQuery(inviterTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(inviterTable, pageable.getSort()))
                .select(inviterTable.fetch(fetcher)));
    }
}
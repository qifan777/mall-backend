package io.qifan.mall.server.inviter.history.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.history.entity.InviteHistory;
import io.qifan.mall.server.inviter.history.entity.InviteHistoryFetcher;
import io.qifan.mall.server.inviter.history.entity.InviteHistoryTable;
import io.qifan.mall.server.inviter.history.entity.dto.InviteHistorySpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InviteHistoryRepository extends JRepository<InviteHistory, String> {
    InviteHistoryTable inviteHistoryTable = InviteHistoryTable.$;
    InviteHistoryFetcher COMPLEX_FETCHER = InviteHistoryFetcher.$.allScalarFields()
            .inviter(UserFetcher.$.nickname().avatar())
            .invitee(UserFetcher.$.nickname().avatar());

    default Page<InviteHistory> findPage(QueryRequest<InviteHistorySpec> queryRequest, Fetcher<InviteHistory> fetcher) {
        InviteHistorySpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return pager(pageable).execute(sql().createQuery(inviteHistoryTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(inviteHistoryTable, pageable.getSort()))
                .select(inviteHistoryTable.fetch(fetcher)));
    }
}
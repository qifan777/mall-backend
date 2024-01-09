package io.qifan.mall.server.user.wechat.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.wechat.entity.UserWeChat;
import io.qifan.mall.server.user.wechat.entity.UserWeChatFetcher;
import io.qifan.mall.server.user.wechat.entity.UserWeChatTable;
import io.qifan.mall.server.user.wechat.entity.dto.UserWeChatSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserWeChatRepository extends JRepository<UserWeChat, String> {

  UserWeChatTable userWeChatTable = UserWeChatTable.$;
  UserWeChatFetcher COMPLEX_FETCHER = UserWeChatFetcher.$.allScalarFields();

  default Page<UserWeChat> findPage(QueryRequest<UserWeChatSpec> queryRequest,
      Fetcher<UserWeChat> fetcher) {
    UserWeChatSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(userWeChatTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(userWeChatTable, pageable.getSort()))
        .select(userWeChatTable.fetch(fetcher)));
  }
}
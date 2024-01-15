package io.qifan.mall.server.user.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.role.entity.RoleFetcher;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.entity.UserTable;
import io.qifan.mall.server.user.entity.dto.UserSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JRepository<User, String> {

  UserTable userTable = UserTable.$;
  UserFetcher COMPLEX_FETCHER = UserFetcher.$.allScalarFields();
  UserFetcher USER_ROLE_FETCHER = UserFetcher.$.allScalarFields().rolesView(RoleFetcher.$.name());

  default Page<User> findPage(QueryRequest<UserSpec> queryRequest, Fetcher<User> fetcher) {
    UserSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(userTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(userTable, pageable.getSort()))
        .select(userTable.fetch(fetcher)));
  }
}
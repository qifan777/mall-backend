package io.qifan.mall.server.role.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.role.entity.Role;
import io.qifan.mall.server.role.entity.RoleFetcher;
import io.qifan.mall.server.role.entity.RoleTable;
import io.qifan.mall.server.role.entity.dto.RoleSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends JRepository<Role, String> {

  RoleTable roleTable = RoleTable.$;
  RoleFetcher COMPLEX_FETCHER = RoleFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  RoleFetcher ROLE_MENU_FETCHER = RoleFetcher.$.allScalarFields().menusView(true);

  default Page<Role> findPage(QueryRequest<RoleSpec> queryRequest, Fetcher<Role> fetcher) {
    RoleSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(roleTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(roleTable, pageable.getSort()))
        .select(roleTable.fetch(fetcher)));
  }
}
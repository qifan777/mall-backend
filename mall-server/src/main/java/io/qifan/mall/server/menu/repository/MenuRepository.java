package io.qifan.mall.server.menu.repository;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.menu.entity.MenuFetcher;
import io.qifan.mall.server.menu.entity.MenuTable;
import io.qifan.mall.server.menu.entity.dto.MenuSpec;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.babyfish.jimmer.sql.fetcher.RecursiveListFieldConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepository extends JRepository<Menu, String> {

  MenuTable menuTable = MenuTable.$;
  MenuFetcher COMPLEX_FETCHER = MenuFetcher.$.allScalarFields()
      .parentId()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  MenuFetcher MENU_TREE_NODE_FETCHER = MenuFetcher.$
      .allScalarFields()
      .parentId();
  MenuFetcher MENU_TREE_FETCHER = MENU_TREE_NODE_FETCHER
      .children(MENU_TREE_NODE_FETCHER.children(MENU_TREE_NODE_FETCHER),
          RecursiveListFieldConfig::recursive);

  default Page<Menu> findPage(QueryRequest<MenuSpec> queryRequest, Fetcher<Menu> fetcher) {
    MenuSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(menuTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(menuTable, pageable.getSort()))
        .select(menuTable.fetch(fetcher)));
  }
}
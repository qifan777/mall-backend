package io.qifan.mall.server.menu.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.menu.entity.MenuTable;
import io.qifan.mall.server.menu.entity.dto.MenuInput;
import io.qifan.mall.server.menu.entity.dto.MenuSpec;
import io.qifan.mall.server.menu.repository.MenuRepository;
import io.qifan.mall.server.role.entity.RoleMenuRelDraft;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final JSqlClient jSqlClient;

    public Menu findById(String id) {
        return menuRepository.findById(id, MenuRepository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(MenuInput menuInput) {
        String id = menuRepository.save(menuInput).id();
        if (!StringUtils.hasText(menuInput.getId())) {
            jSqlClient.insert(RoleMenuRelDraft.$.produce(draft -> {
                draft.applyMenu(menuDraft->menuDraft.setId(id));
                draft.applyRole(roleDraft -> roleDraft.setId("1"));
            }));
        }
        return id;
    }

    public Page<Menu> query(QueryRequest<MenuSpec> queryRequest) {
        return menuRepository.findPage(queryRequest, MenuRepository.COMPLEX_FETCHER);
    }

    public List<Menu> queryMenuTree() {
        MenuTable menuTable = MenuTable.$;
        return menuRepository.sql()
                .createQuery(menuTable)
                .where(menuTable.parentId().isNull())
                .select(menuTable.fetch(MenuRepository.MENU_TREE_FETCHER))
                .execute();
    }

    public boolean delete(List<String> ids) {
        menuRepository.deleteAllById(ids);
        return true;
    }
}
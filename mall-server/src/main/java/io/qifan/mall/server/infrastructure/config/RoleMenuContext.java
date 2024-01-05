package io.qifan.mall.server.infrastructure.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.role.entity.RoleFetcher;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.entity.UserRoleRelFetcher;
import io.qifan.mall.server.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RoleMenuContext implements StpInterface {
    private final UserRepository userRepository;

    public RoleMenuContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserFetcher userFetcher = UserFetcher.$.roles(UserRoleRelFetcher.$.role(RoleFetcher.$.menus(true)));

    @Override
    public List<String> getPermissionList(Object o, String s) {
        log.info("查询权限");
        ArrayList<String> menuNames = new ArrayList<>();
        userRepository.findById(StpUtil.getLoginIdAsString(), userFetcher)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError))
                .roles()
                .forEach(role -> menuNames.addAll(role.role().menus().stream().map(roleMenuRel -> roleMenuRel.menu().name()).toList()));
        return menuNames;

    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        log.info("查询角色");
        return userRepository.findById(StpUtil.getLoginIdAsString(), userFetcher)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError))
                .roles()
                .stream()
                .map(userRoleRel -> userRoleRel.role().name())
                .toList();
    }
}

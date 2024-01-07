package io.qifan.mall.server.user.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.sms.SmsService;
import io.qifan.mall.server.auth.LoginDevice;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.menu.entity.MenuTable;
import io.qifan.mall.server.menu.repository.MenuRepository;
import io.qifan.mall.server.role.entity.Role;
import io.qifan.mall.server.role.entity.RoleMenuRelTable;
import io.qifan.mall.server.role.repository.RoleRepository;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.UserDraft;
import io.qifan.mall.server.user.entity.UserFetcher;
import io.qifan.mall.server.user.entity.UserTable;
import io.qifan.mall.server.user.entity.dto.UserInput;
import io.qifan.mall.server.user.entity.dto.UserRegisterInput;
import io.qifan.mall.server.user.entity.dto.UserSpec;
import io.qifan.mall.server.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {

  private final MenuRepository menuRepository;
  private final UserRepository userRepository;
  private final SmsService smsService;
  private final JSqlClient jSqlClient;

  public User findById(String id) {
    return userRepository.findById(id, UserRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(UserInput userInput) {
    if (userInput.getPassword().length() <= 16) {
      userInput.setPassword(BCrypt.hashpw(userInput.getPassword()));
    }
    User user = userInput.toEntity();
    return userRepository.save(UserDraft.$.produce(user, draft -> {
      Arrays.stream(userInput.getRoleIds()).forEach(roleId -> {
        draft.addIntoRoles(userRole -> {
          userRole.applyRole(role -> role.setId(roleId));
          userRole.setUser(user);
        });
      });
    })).id();
  }

  public Page<User> query(QueryRequest<UserSpec> queryRequest) {
    return userRepository.findPage(queryRequest, UserRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    userRepository.deleteAllById(ids);
    return true;
  }

  public User getUserInfo() {
    return userRepository.findById(StpUtil.getLoginIdAsString(), UserRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public SaTokenInfo register(UserRegisterInput registerInput) {
    boolean checked = smsService.checkSms(registerInput.getPhone(), registerInput.getCode());
    if (!checked) {
      throw new BusinessException(ResultCode.ValidateError, "验证码错误");
    }
    UserTable userTable = UserTable.$;
    userRepository.sql().createQuery(userTable)
        .where(userTable.phone().eq(registerInput.getPhone()))
        .select(userTable).fetchOptional()
        .ifPresent((user) -> {
          throw new BusinessException(ResultCode.StatusHasValid, "用户已经存在");
        });
    StpUtil.login(userRepository.save(UserDraft.$.produce(registerInput.toEntity(), draft -> {
      draft.setNickname("默认用户").setPassword(BCrypt.hashpw(draft.password()));
    })).id(), LoginDevice.BROWSER);
    return StpUtil.getTokenInfo();
  }

  public List<Role> getUserRoles(String id) {
    return userRepository.findById(id, UserFetcher.$.rolesView(RoleRepository.SIMPLE_FETCHER))
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError)).rolesView();
  }

  public List<Menu> getUserMenusTree(String id) {
    MenuTable menuTable = MenuTable.$;
    RoleMenuRelTable roleMenuRelTable = RoleMenuRelTable.$;
    List<String> roleIds = getUserRoles(id).stream().map(BaseEntity::id).toList();
    return menuRepository.sql().createQuery(menuTable)
        .where(menuTable.id().in(jSqlClient.createSubQuery(roleMenuRelTable)
            .where(roleMenuRelTable.role().id().in(roleIds)).select(roleMenuRelTable.menuId())))
        .where(menuTable.parentId().isNull())
        .select(menuTable.fetch(MenuRepository.MENU_TREE_FETCHER)).execute();
  }

  public List<Menu> getUserMenus(String id) {
    MenuTable menuTable = MenuTable.$;
    RoleMenuRelTable roleMenuRelTable = RoleMenuRelTable.$;
    List<String> roleIds = getUserRoles(id).stream().map(BaseEntity::id).toList();
    return menuRepository.sql().createQuery(menuTable)
        .where(menuTable.id().in(jSqlClient.createSubQuery(roleMenuRelTable)
            .where(roleMenuRelTable.role().id().in(roleIds)).select(roleMenuRelTable.menuId())))
        .select(menuTable.fetch(MenuRepository.COMPLEX_FETCHER)).execute();
  }
}

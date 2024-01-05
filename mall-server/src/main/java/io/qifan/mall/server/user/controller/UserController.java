package io.qifan.mall.server.user.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.menu.repository.MenuRepository;
import io.qifan.mall.server.role.entity.Role;
import io.qifan.mall.server.role.repository.RoleRepository;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.dto.UserInput;
import io.qifan.mall.server.user.entity.dto.UserRegisterInput;
import io.qifan.mall.server.user.entity.dto.UserSpec;
import io.qifan.mall.server.user.repository.UserRepository;
import io.qifan.mall.server.user.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("{id}")

  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = UserRepository.class) User findById(
      @PathVariable String id) {
    return userService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = UserRepository.class) User> query(
      @RequestBody QueryRequest<UserSpec> queryRequest) {
    return userService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated UserInput user) {
    return userService.save(user);
  }

  @PostMapping("delete")
  public boolean delete(@RequestBody List<String> ids) {
    return userService.delete(ids);
  }

  @GetMapping("info")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = UserRepository.class) User userInfo() {
    return userService.getUserInfo();
  }

  @PostMapping("register")
  public SaTokenInfo register(@RequestBody @Validated UserRegisterInput registerInput) {
    return userService.register(registerInput);
  }

  @SaCheckRole("管理员")
  @GetMapping("{id}/roles")
  public List<@FetchBy(value = "SIMPLE_FETCHER", ownerType = RoleRepository.class) Role> getUserRoles(
      @PathVariable String id) {
    return userService.getUserRoles(id);
  }

  @SaCheckRole("管理员")
  @GetMapping("{id}/menus/tree")
  public List<@FetchBy(value = "MENU_TREE_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenusTree(
      @PathVariable String id) {
    return userService.getUserMenusTree(id);
  }

  @SaCheckRole("管理员")
  @GetMapping("{id}/menus")
  public List<@FetchBy(value = "COMPLEX_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenus(
      @PathVariable String id) {
    return userService.getUserMenusTree(id);
  }

  @GetMapping("menus/tree")
  public List<@FetchBy(value = "MENU_TREE_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenusTree() {
    return userService.getUserMenusTree(StpUtil.getLoginIdAsString());
  }

  @GetMapping("menus")
  public List<@FetchBy(value = "COMPLEX_FETCHER", ownerType = MenuRepository.class) Menu> getUserMenus() {
    return userService.getUserMenus(StpUtil.getLoginIdAsString());
  }
}

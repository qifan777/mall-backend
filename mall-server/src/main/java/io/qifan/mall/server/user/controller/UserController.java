package io.qifan.mall.server.user.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.User;
import io.qifan.mall.server.user.entity.dto.UserInput;
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
  public Boolean delete(@RequestBody List<String> ids) {
    return userService.delete(ids);
  }
}
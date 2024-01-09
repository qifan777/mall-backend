package io.qifan.mall.server.user.wechat.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.wechat.entity.UserWeChat;
import io.qifan.mall.server.user.wechat.entity.dto.UserWeChatInput;
import io.qifan.mall.server.user.wechat.entity.dto.UserWeChatSpec;
import io.qifan.mall.server.user.wechat.model.UserWeChatRegisterInput;
import io.qifan.mall.server.user.wechat.repository.UserWeChatRepository;
import io.qifan.mall.server.user.wechat.service.UserWeChatService;
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
@RequestMapping("userWeChat")
@AllArgsConstructor
public class UserWeChatController {

  private final UserWeChatService userWeChatService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = UserWeChatRepository.class) UserWeChat findById(
      @PathVariable String id) {
    return userWeChatService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = UserWeChatRepository.class) UserWeChat> query(
      @RequestBody QueryRequest<UserWeChatSpec> queryRequest) {
    return userWeChatService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated UserWeChatInput userWeChat) {
    return userWeChatService.save(userWeChat);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return userWeChatService.delete(ids);
  }

  @PostMapping("register")
  public SaTokenInfo register(@RequestBody @Validated UserWeChatRegisterInput registerInput) {
    return  userWeChatService.register(registerInput);
  }
}
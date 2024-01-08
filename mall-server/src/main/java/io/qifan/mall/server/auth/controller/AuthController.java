package io.qifan.mall.server.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.qifan.mall.server.auth.model.PhonePasswordAuth;
import io.qifan.mall.server.auth.service.IAuthStrategy;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

  private final Map<String, IAuthStrategy> authStrategyMap;

  @PostMapping("phone-password")
  public SaTokenInfo authByPhonePassword(@RequestBody PhonePasswordAuth phonePasswordAuth) {
    return authStrategyMap.get(IAuthStrategy.PASSWORD).auth(phonePasswordAuth);
  }
}

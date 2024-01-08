package io.qifan.mall.server.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.qifan.mall.server.auth.model.AuthModel;

public interface IAuthStrategy {

  String PASSWORD = "password";
  String WECHAT = "wechat";

  SaTokenInfo auth(AuthModel authModel);
}

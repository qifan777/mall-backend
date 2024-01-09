package io.qifan.mall.server.user.wechat.model;

import lombok.Data;

@Data
public class UserWeChatRegisterInput {

  private String loginCode;
  private String phone;
  private String code;
}

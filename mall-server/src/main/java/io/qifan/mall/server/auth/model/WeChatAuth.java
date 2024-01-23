package io.qifan.mall.server.auth.model;

import lombok.Data;


@Data
public class WeChatAuth implements AuthModel {

  private String loginCode;
}

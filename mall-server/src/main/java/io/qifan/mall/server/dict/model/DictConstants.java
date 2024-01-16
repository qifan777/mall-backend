package io.qifan.mall.server.dict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DictConstants {

  public static final String MENU_TYPE = "MENU_TYPE";
  public static final String GENDER = "GENDER";

  @Getter
  @AllArgsConstructor
  public enum MenuType {
    BUTTON(2, "按钮", "BUTTON", 1002, "菜单类型", "MENU_TYPE", 2),
    PAGE(0, "页面", "PAGE", 1002, "菜单类型", "MENU_TYPE", 1),
    DIRECTORY(1, "目录", "DIRECTORY", 1002, "菜单类型", "MENU_TYPE", 0),
    ;
    final int keyId;
    final String keyName;
    final String keyEnName;
    final int dictId;
    final String dictName;
    final String dictEnName;
    final int orderNum;
  }

  @Getter
  @AllArgsConstructor
  public enum Gender {
    PRIVATE(2, "保密", "PRIVATE", 1001, "性别", "GENDER", 0),
    MALE(0, "男", "MALE", 1001, "性别", "GENDER", 0),
    FEMALE(1, "女", "FEMALE", 1001, "性别", "GENDER", 1),
    ;
    final int keyId;
    final String keyName;
    final String keyEnName;
    final int dictId;
    final String dictName;
    final String dictEnName;
    final int orderNum;
  }
}

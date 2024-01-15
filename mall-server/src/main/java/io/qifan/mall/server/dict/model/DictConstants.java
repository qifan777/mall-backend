package io.qifan.mall.server.dict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DictConstants {

  public static final String GENDER = "GENDER";
  public static final String SYSTEM_CONSTANT = "SYSTEM_CONSTANT";

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

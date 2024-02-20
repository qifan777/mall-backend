package io.qifan.mall.server.dict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DictConstants {

  public static final String PAY_TYPE = "PAY_TYPE";
  public static final String MENU_TYPE = "MENU_TYPE";
  public static final String PRODUCT_ORDER_STATUS = "PRODUCT_ORDER_STATUS";
  public static final String GENDER = "GENDER";

  @Getter
  @AllArgsConstructor
  public enum PayType {
    WE_CHAT_PAY(0, "微信支付", "WE_CHAT_PAY", 1004, "支付类型", "PAY_TYPE", 0),
    ALI_PAY(1, "支付宝", "ALI_PAY", 1004, "支付方式", "PAY_TYPE", 0),
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
  public enum ProductOrderStatus {
    CLOSED(4, "已关闭", "CLOSED", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS", 0),
    REFUNDING(5, "退款中", "REFUNDING", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS", 0),
    TO_BE_RECEIVED(2, "待收货", "TO_BE_RECEIVED", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS", 0),
    TO_BE_PAID(0, "待付款", "TO_BE_PAID", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS", 0),
    TO_BE_EVALUATED(3, "待评价", "TO_BE_EVALUATED", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS",
        0),
    TO_BE_CREATE(6, "待创建", "TO_BE_CREATE", 1003, "商品订单状态", "PRODUCT_ORDER_STATUS", 0),
    TO_BE_DELIVERED(1, "待发货", "TO_BE_DELIVERED", 1003, "商品订单状态	", "PRODUCT_ORDER_STATUS",
        0),
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

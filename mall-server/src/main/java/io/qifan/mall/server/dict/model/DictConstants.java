package io.qifan.mall.server.dict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DictConstants {

  public static final String REFUND_STATUS = "REFUND_STATUS";
  public static final String COUPON_TYPE = "COUPON_TYPE";
  public static final String PAY_TYPE = "PAY_TYPE";
  public static final String MENU_TYPE = "MENU_TYPE";
  public static final String PRODUCT_ORDER_STATUS = "PRODUCT_ORDER_STATUS";
  public static final String GENDER = "GENDER";
  public static final String COUPON_SCOPE = "COUPON_SCOPE";
  public static final String COUPON_USE_STATUS = "COUPON_USE_STATUS";
  public static final String COUPON_RECEIVE_TYPE = "COUPON_RECEIVE_TYPE";
  public static final String WALLET_RECORD_TYPE = "WALLET_RECORD_TYPE";
  public static final String AGENT_LEVEL = "AGENT_LEVEL";

  @Getter
  @AllArgsConstructor
  public enum RefundStatus {
    APPROVED(2, "已同意", "APPROVED", 1005, "退款状态", "REFUND_STATUS", 0),
    TO_BE_APPROVE(1, "待审批", "TO_BE_APPROVE", 1005, "退款状态", "REFUND_STATUS", 0),
    REJECTED(3, "已拒绝", "REJECTED", 1005, "退款状态", "REFUND_STATUS", 0),
    TO_BE_CREATE(0, "待创建", "TO_BE_CREATE", 1005, "退款状态", "REFUND_STATUS", 0),
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
  public enum CouponType {
    DISCOUNT(0, "折扣券", "DISCOUNT", 1006, "优惠券类型", "COUPON_TYPE", 0),
    REDUCE(1, "满减券", "REDUCE", 1006, "优惠券类型", "COUPON_TYPE", 0),
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
  public enum PayType {
    WE_CHAT_PAY(0, "微信支付", "WE_CHAT_PAY", 1004, "支付类型", "PAY_TYPE", 0),
    ALI_PAY(1, "支付宝", "ALI_PAY", 1004, "支付类型", "PAY_TYPE", 0),
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

  @Getter
  @AllArgsConstructor
  public enum CouponScope {
    CATEGORY(2, "品类券", "CATEGORY", 1007, "优惠券使用范围", "COUPON_SCOPE", 0),
    PRODUCT(1, "商品券", "PRODUCT", 1007, "优惠券使用范围", "COUPON_SCOPE", 0),
    GENERAL(0, "通用券", "GENERAL", 1007, "优惠券使用范围", "COUPON_SCOPE", 0),
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
  public enum CouponUseStatus {
    USED(1, "已使用", "USED", 1008, "优惠券使用状态", "COUPON_USE_STATUS", 0),
    EXPIRED(2, "已过期", "EXPIRED", 1008, "优惠券使用状态", "COUPON_USE_STATUS", 0),
    UNUSED(0, "未使用", "UNUSED", 1008, "优惠券使用状态", "COUPON_USE_STATUS", 0),
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
  public enum CouponReceiveType {
    GIFT(0, "系统赠送", "GIFT", 1009, "优惠券获取方式", "COUPON_RECEIVE_TYPE", 0),
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
  public enum WalletRecordType {
    REBATE(0, "返佣", "REBATE", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
    RECHARGE(1, "充值", "RECHARGE", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
    WITHDRAW(2, "提现", "WITHDRAW", 1010, "钱包操作类型", "WALLET_RECORD_TYPE", 0),
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
  public enum AgentLevel {
    FIRST(0, "1级", "FIRST", 1011, "代理等级", "AGENT_LEVEL", 0),
    SECOND(1, "2级", "SECOND", 1011, "代理等级", "AGENT_LEVEL", 0),
    THIRD(2, "3级", "THIRD", 1011, "代理等级", "AGENT_LEVEL", 0),
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

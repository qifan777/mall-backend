package io.qifan.mall.server.payment.entity;

import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.dict.model.DictConstants.PayType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.babyfish.jimmer.sql.Entity;


@Entity
public interface Payment extends BaseEntity {

  @GenField(value = "支付类型", order = 1, type = ItemType.SELECTABLE, dictEnName = DictConstants.PAY_TYPE)
  PayType payType();

  @NotNull
  @GenField(value = "实付金额", order = 2, type = ItemType.INPUT_NUMBER)
  BigDecimal payAmount();

  @NotNull
  @GenField(value = "配送费", order = 3, type = ItemType.INPUT_NUMBER)
  BigDecimal deliveryFee();

  @NotNull
  @GenField(value = "优惠卷减免", order = 4, type = ItemType.INPUT_NUMBER)
  BigDecimal couponAmount();

  @NotNull
  @GenField(value = "VIP减免", order = 5, type = ItemType.INPUT_NUMBER)
  BigDecimal vipAmount();

  @NotNull
  @GenField(value = "商品金额", order = 6, type = ItemType.INPUT_NUMBER)
  BigDecimal productAmount();

  @GenField(value = "支付时间", order = 7, type = ItemType.DATETIME)
  @Null
  LocalDateTime payTime();

  @GenField(value = "支付订单号", order = 8)
  @Null
  String tradeNo();
}


package io.qifan.mall.server.coupon.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;

/**
 * Entity for table "coupon"
 */
@GenEntity
@Entity
public interface Coupon extends BaseEntity {

  @GenField(value = "名称", order = -1)
  @Key
  String name();

  @GenField(value = "消费门槛", order = 0)
  @NotNull
  BigDecimal thresholdAmount();

  @GenField(value = "发放数量", order = 1)
  int releasedQuantity();

  @GenField(value = "生效时间", order = 2, type = ItemType.DATETIME)
  @NotNull
  LocalDateTime effectiveDate();

  @GenField(value = "失效时间", order = 3, type = ItemType.DATETIME)
  @NotNull
  LocalDateTime expirationDate();

  @GenField(value = "优惠券类型", order = 4, type = ItemType.SELECTABLE, dictId = 1003)
  String couponType();

  @GenField(value = "使用品类", order = 5, type = ItemType.SELECTABLE, dictId = 1004)
  String scopeType();

  @Null
  @GenField(value = "优惠金额", order = 7, type = ItemType.INPUT_NUMBER)
  BigDecimal amount();

  @Null
  @GenField(value = "折扣", order = 8, type = ItemType.INPUT_NUMBER)
  BigDecimal discount();
}


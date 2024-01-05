package io.qifan.mall.server.coupon.use.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToOne;

/**
 * Entity for table "coupon_use_record"
 */
@Entity
@GenEntity
public interface CouponUseRecord extends BaseEntity {

  @OneToOne
  @GenField(value = "优惠券信息")
  CouponUser couponUser();

  @GenField(value = "订单id")
  String orderId();

}


package io.qifan.mall.server.coupon.user.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.coupon.root.entity.Coupon;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.dict.model.DictConstants.CouponReceiveType;
import io.qifan.mall.server.dict.model.DictConstants.CouponUseStatus;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.user.entity.User;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;


@Entity
@GenEntity
@Table(name = "coupon_user_rel")
public interface CouponUser extends BaseEntity {

  @GenField(value = "获得方式", order = 0, type = ItemType.SELECTABLE, dictEnName = DictConstants.COUPON_RECEIVE_TYPE)
  CouponReceiveType receiveType();

  @GenField(value = "优惠券", order = 1)
  @ManyToOne
  Coupon coupon();

  @IdView
  String couponId();

  @GenField(value = "拥有者", order = 2)
  @ManyToOne
  User user();

  @IdView
  String userId();

  @GenField(value = "优惠券状态", order = 3, type = ItemType.SELECTABLE, dictEnName = DictConstants.COUPON_USE_STATUS)
  CouponUseStatus status();
}


package io.qifan.mall.server.order.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.address.entity.Address;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.dict.model.DictConstants.ProductOrderStatus;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.payment.entity.Payment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OneToOne;

@Entity
@GenEntity
public interface ProductOrder extends BaseEntity {

  @GenField(value = "备注", order = 0)
  String remark();

  @GenField(value = "订单状态", order = 1, type = ItemType.SELECTABLE, dictEnName = DictConstants.PRODUCT_ORDER_STATUS)
  ProductOrderStatus status();

  @GenField(value = "物流单号")
  @Null
  String trackingNumber();

  @OneToOne
  Payment payment();

  @OneToOne
  Address address();

  @OneToOne
  @Null
  CouponUser couponUser();

  @OneToMany(mappedBy = "productOrder")
  @Valid
  @Size(min = 1, message = "订单至少需要一个商品")
  @NotNull(message = "订单至少需要一个商品")
  List<ProductOrderItem> items();
}


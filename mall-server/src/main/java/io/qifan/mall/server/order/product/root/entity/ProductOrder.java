package io.qifan.mall.server.order.product.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.order.product.item.entity.ProductOrderItem;
import io.qifan.mall.server.order.root.entity.BaseOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OneToOne;

/**
 * Entity for table "product_order"
 */
@Entity
@GenEntity
public interface ProductOrder extends BaseEntity {

  @GenField(value = "基础订单信息", order = 0)
  @OneToOne
  BaseOrder baseOrder();

  @OneToMany(mappedBy = "productOrder")
  @Valid
  @Size(min = 1, message = "订单至少需要一个商品")
  @NotNull(message = "订单至少需要一个商品")
  List<ProductOrderItem> items();

  @GenField(value = "订单状态", order = 1)
  String status();

  @GenField(value = "优惠券", order = 2)
  @ManyToOne
  CouponUser couponUser();

  @GenField(value = "配送费", order = 3)
  BigDecimal deliveryFee();

  @GenField(value = "优惠卷减免", order = 4)
  BigDecimal couponAmount();

  @GenField(value = "VIP减免", order = 5)
  BigDecimal vipAmount();

  @GenField(value = "商品价格", order = 6)
  BigDecimal productAmount();
}


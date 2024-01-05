package io.qifan.mall.server.order.product.item.entity;

import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.order.product.root.entity.ProductOrder;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * Entity for table "product_order_item"
 */
@Entity
public interface ProductOrderItem extends BaseEntity {

  @ManyToOne
  ProductOrder productOrder();

  @GenField(value = "商品sku", order = 0)
  @ManyToOne
  ProductSku productSku();

  @IdView
  @NotBlank(message = "商品sku不能为空")
  String productSkuId();

  @GenField(value = "商品数量", order = 1)
  @Column(name = "count")
  @Min(value = 1, message = "商品数量不能少于1")
  int productCount();
}


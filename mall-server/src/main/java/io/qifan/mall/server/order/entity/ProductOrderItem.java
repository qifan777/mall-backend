package io.qifan.mall.server.order.entity;

import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;

@Entity
public interface ProductOrderItem extends BaseEntity {

  @ManyToOne
  @Key
  ProductOrder productOrder();

  @ManyToOne
  @Key
  ProductSku productSku();

  @IdView
  String productOrderId();

  @IdView
  @NotBlank(message = "商品sku不能为空")
  String productSkuId();

  @Column(name = "count")
  @Min(value = 1, message = "商品数量不能少于1")
  int skuCount();
}


package io.qifan.mall.server.product.sku.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.root.entity.Product;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;

/**
 * Entity for table "product_sku"
 */
@GenEntity
@Entity
public interface ProductSku extends BaseEntity {

  @GenField(value = "属性值组合", order = 0)
  @Column(name = "`values`")
  @Key
  String values();

  @GenField(value = "Sku名称", order = 1)
  String name();

  @ManyToOne
  @OnDissociate(DissociateAction.DELETE)
  @Key
  Product product();

  @GenField(value = "封面", order = 2)
  String cover();

  @NotNull
  @GenField(value = "价格", type = ItemType.INPUT_NUMBER, order = 3)
  BigDecimal price();

  @GenField(value = "库存", order = 4)
  int stock();

  @GenField(value = "描述", order = 5)
  String description();
}


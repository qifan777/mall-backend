package io.qifan.mall.server.product.sku.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.root.entity.Product;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.EnableDtoGeneration;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;
import org.babyfish.jimmer.sql.Serialized;

@GenEntity
@Entity
public interface ProductSku extends BaseEntity {

  @Column(name = "`values`")
  @Key
  @Serialized
  List<String> values();

  @GenField(value = "Sku名称", order = 0)
  String name();

  @ManyToOne
  @OnDissociate(DissociateAction.DELETE)
  @Key
  Product product();

  @IdView
  @GenField(value = "商品", order = 1)
  String productId();

  @GenField(value = "封面", type = ItemType.PICTURE, order = 2)
  String cover();

  @NotNull
  @GenField(value = "价格", type = ItemType.INPUT_NUMBER, order = 3)
  BigDecimal price();

  @GenField(value = "库存", order = 4)
  int stock();

  @GenField(value = "描述", order = 5)
  String description();
}

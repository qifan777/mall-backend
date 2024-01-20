package io.qifan.mall.server.product.category.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;

@GenEntity
@Entity
public interface ProductCategory extends BaseEntity {

  @GenField(value = "名称", order = 0)
  String name();

  @Null
  @GenField(value = "父亲id", order = 1)
  String parentId();

  @GenField(value = "图片", type = ItemType.PICTURE, order = 2)
  @Null
  String image();

  @GenField(value = "描述", order = 3)
  String description();

  @GenField(value = "排序号", order = 4)
  Integer sortOrder();
}
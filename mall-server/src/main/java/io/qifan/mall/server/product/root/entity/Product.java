package io.qifan.mall.server.product.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.attribute.entity.ProductAttribute;
import io.qifan.mall.server.product.category.entity.ProductCategory;
import io.qifan.mall.server.product.sku.entity.ProductSku;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entity for table "product"
 */
@Entity
@GenEntity
public interface Product extends BaseEntity {

    @GenField(value = "名称", order = 0)
    String name();

    @NotNull
    @GenField(value = "价格", type = ItemType.INPUT_NUMBER, order = 1)
    BigDecimal price();

    @GenField(value = "封面", type = ItemType.PICTURE, order = 2)
    String image();

    @GenField(value = "品牌", order = 3)
    String brand();

    @ManyToOne
    ProductCategory category();

    @IdView
    String categoryId();

    @GenField(value = "库存", order = 4, type = ItemType.INPUT_NUMBER)
    int stock();

    @GenField(value = "描述", order = 5)
    String description();

    @GenField(value = "标签", order = 6)
    String tags();

    @GenField(value = "特性", order = 7)
    String features();

    @GenField(value = "规格", order = 8)
    String specs();

    @OneToMany(mappedBy = "product")
    List<ProductAttribute> attributes();

    @OneToMany(mappedBy = "product")
    List<ProductSku> skuList();

}


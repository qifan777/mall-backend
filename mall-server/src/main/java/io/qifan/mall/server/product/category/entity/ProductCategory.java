package io.qifan.mall.server.product.category.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.root.entity.Product;
import javax.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;

import java.util.List;

/**
 * Entity for table "product_category"
 */
@GenEntity
@Entity
public interface ProductCategory extends BaseEntity {

    @GenField(value = "名称", order = 0)
    String name();

    @Null
    @ManyToOne
    ProductCategory parent();

    @IdView
    @Null
    String parentId();

    @GenField(value = "图片", type = ItemType.PICTURE, order = 1)
    String image();

    @GenField(value = "描述", order = 2)
    String description();

    @GenField(value = "排序号", order = 3)
    Integer sortOrder();

    @OneToMany(mappedBy = "parent")
    List<ProductCategory> children();

    @OneToMany(mappedBy = "category")
    List<Product> products();
}


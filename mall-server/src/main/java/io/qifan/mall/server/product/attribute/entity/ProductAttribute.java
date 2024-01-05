package io.qifan.mall.server.product.attribute.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.product.root.entity.Product;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;

/**
 * Entity for table "product_attribute"
 */
@GenEntity
@Entity
public interface ProductAttribute extends BaseEntity {
    @GenField(value = "属性名称", order = 0)
    @Key
    String name();

    @GenField(value = "属性值", order = 1)
    @Column(name = "`values`")
    String values();

    @ManyToOne
    @Key
    @OnDissociate(DissociateAction.DELETE)
    Product product();
}


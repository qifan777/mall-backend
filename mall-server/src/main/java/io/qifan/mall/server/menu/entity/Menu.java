package io.qifan.mall.server.menu.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.role.entity.RoleMenuRel;
import java.util.List;
import javax.validation.constraints.Null;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;
import org.babyfish.jimmer.sql.OneToMany;

@GenEntity
@Entity
public interface Menu extends BaseEntity {
    @GenField(value = "菜单名称", order = 0)
    String name();

    @Null
    @ManyToOne
    @OnDissociate(DissociateAction.SET_NULL)
    Menu parent();

    @IdView
    @GenField(value = "父菜单Id", order = 1)
    @Null
    String parentId();

    @GenField(value = "路由路径", order = 2)
    String path();

    @GenField(value = "排序号")
    Integer orderNum();

    @GenField(value = "菜单类型", type = ItemType.SELECTABLE, dictId = 1002)
    String menuType();

    @GenField(value = "图标", type = ItemType.PICTURE)
    @Null
    String icon();

    @OneToMany(mappedBy = "parent")
    List<Menu> children();

    @OneToMany(mappedBy = "menu")
    List<RoleMenuRel> roles();
}

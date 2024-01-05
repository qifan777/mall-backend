package io.qifan.mall.server.role.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.user.entity.UserRoleRel;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToManyView;
import org.babyfish.jimmer.sql.OneToMany;

import java.util.List;

@GenEntity
@Entity
public interface Role extends BaseEntity {
    @GenField(value = "角色名称")
    @Key
    String name();

    @OneToMany(mappedBy = "role")
    List<RoleMenuRel> menus();

    @OneToMany(mappedBy = "role")
    List<UserRoleRel> users();

    @ManyToManyView(prop = "menus")
    List<Menu> menusView();
}

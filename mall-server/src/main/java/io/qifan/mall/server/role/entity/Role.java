package io.qifan.mall.server.role.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.user.entity.UserRoleRel;
import java.util.List;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.OneToMany;

@GenEntity
@Entity
public interface Role extends BaseEntity {
    @GenField(value = "角色名称")
    @Key
    String name();
    @OneToMany(mappedBy = "role")
    List<UserRoleRel> users();
}

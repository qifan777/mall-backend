package io.qifan.mall.server.user.entity;

import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.role.entity.Role;
import org.babyfish.jimmer.sql.*;

@Entity
public interface UserRoleRel extends BaseEntity {
    @ManyToOne
    @Key
    @OnDissociate(DissociateAction.DELETE)
    User user();

    @ManyToOne
    @Key
    Role role();
}

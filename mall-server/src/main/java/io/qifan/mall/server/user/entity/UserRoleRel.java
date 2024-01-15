package io.qifan.mall.server.user.entity;

import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.role.entity.Role;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;

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

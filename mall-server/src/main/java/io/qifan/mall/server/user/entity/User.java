package io.qifan.mall.server.user.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.mall.server.infrastructure.jimmer.UUIDIdGenerator;
import io.qifan.mall.server.role.entity.Role;
import java.util.List;
import javax.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToManyView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OneToOne;

@Entity
@GenEntity
public interface User extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @Key
  @GenField(value = "手机号", order = -1)
  String phone();

  String password();

  @GenField(value = "昵称", order = 0)
  String nickname();

  @GenField(value = "头像", type = ItemType.PICTURE, order = 1)
  @Null
  String avatar();

  @GenField(value = "性别", order = 3, type = ItemType.SELECTABLE, dictId = 1001)
  @Null
  String gender();

  @OneToMany(mappedBy = "user")
  List<UserRoleRel> roles();

  @Null
  @OneToOne(mappedBy = "user")
  UserWechat wechat();

  @ManyToManyView(
      prop = "roles",
      deeperProp = "role"
  )
  List<Role> rolesView();
}

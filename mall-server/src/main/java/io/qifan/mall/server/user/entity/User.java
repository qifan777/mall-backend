package io.qifan.mall.server.user.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.mall.server.infrastructure.jimmer.UUIDIdGenerator;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;

@Entity
@GenEntity
public interface User extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @Key
  @GenField(value = "手机号", order = -1)
  String phone();

  @GenField(value = "密码", order = 0)
  String password();

  @GenField(value = "昵称", order = 1)
  @Null
  String nickname();

  @GenField(value = "头像", type = ItemType.PICTURE, order = 2)
  @Null
  String avatar();

  @GenField(value = "性别", order = 3)
  @Null
  String gender();
}

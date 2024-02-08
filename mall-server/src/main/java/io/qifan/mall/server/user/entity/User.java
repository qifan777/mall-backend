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

@GenEntity
@Entity
public interface User extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @GenField(value = "手机号", order = 0)
  @Key
  String phone();

  @GenField(value = "密码", order = 1)
  String password();

  @GenField(value = "昵称", order = 2)
  @Null
  String nickname();

  @GenField(value = "头像", order = 3, type = ItemType.PICTURE)
  @Null
  String avatar();

  @GenField(value = "性别", order = 4)
  @Null
  String gender();
}

package io.qifan.mall.server.user.wechat.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.mall.server.infrastructure.jimmer.UUIDIdGenerator;
import io.qifan.mall.server.user.entity.User;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Table;


/**
 * Entity for table "user_wechat"
 */
@Entity
@GenEntity
@Table(name = "user_wechat")
public interface UserWeChat extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @Key
  @GenField(value = "openId", order = 0)
  String openId();

  @OneToOne
  User user();
}


package io.qifan.mall.server.user.entity;

import io.qifan.mall.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.mall.server.infrastructure.jimmer.UUIDIdGenerator;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.OneToOne;


/**
 * Entity for table "user_wechat"
 */
@Entity
public interface UserWechat extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @Key
  String openId();

  @OneToOne
  User user();
}


package io.qifan.mall.server.address.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;

/**
 * Entity for table "address"
 */
@Entity
@GenEntity
public interface Address extends BaseEntity {

  @GenField(value = "维度", order = 0, type = ItemType.INPUT_NUMBER)
  double latitude();

  @GenField(value = "经度", order = 1, type = ItemType.INPUT_NUMBER)
  double longitude();

  @GenField(value = "地址信息", order = 2)
  String address();

  @GenField(value = "地址详情", order = 3)
  String details();

  @GenField(value = "省份", order = 4)
  String province();

  @GenField(value = "城市", order = 5)
  String city();

  @GenField(value = "区县", order = 6)
  String district();

  @GenField(value = "电话", order = 7)
  String phoneNumber();

  @GenField(value = "姓名", order = 8)
  String realName();

  @GenField(value = "是否置顶", order = 9)
  boolean top();
}


package io.qifan.mall.server.order.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.mall.server.address.entity.Address;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.payment.entity.Payment;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToOne;

/**
 * Entity for table "base_order"
 */
@Entity
@GenEntity
public interface BaseOrder extends BaseEntity {

  @OneToOne
  Payment payment();

  @OneToOne
  Address address();

  String remark();

  String status();
}


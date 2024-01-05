package io.qifan.mall.server.payment.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.babyfish.jimmer.sql.Entity;

/**
 * Entity for table "payment"
 */
@Entity
@GenEntity
public interface Payment extends BaseEntity {

  String payType();

  @NotNull
  BigDecimal payAmount();

  String tradeNo();
}


package io.qifan.mall.server.refund.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.mall.server.dict.model.DictConstants.RefundStatus;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import org.babyfish.jimmer.sql.Entity;

@GenEntity
@Entity
public interface RefundRecord extends BaseEntity {

  @GenField(value = "订单编号")
  String orderId();

  @Null
  @GenField(value = "理由")
  String reason();

  @GenField(value = "微信支付退款号")
  String refundId();

  @Null
  @GenField(value = "物流单号")
  String trackingNumber();

  @NotNull
  @GenField(value = "退款金额")
  BigDecimal amount();

  @NotNull
  @GenField(value = "订单原始金额")
  BigDecimal originalAmount();

  @GenField(value = "退款状态")
  RefundStatus status();
}


package io.qifan.mall.server.agent.level.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import java.math.BigDecimal;
import javax.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;

/**
 * Entity for table "agent_level"
 */
@GenEntity
@Entity
public interface AgentLevel extends BaseEntity {

  @Null
  String tenantId();

  /**
   * 等级名称
   */
  @GenField(value = "代理等级", type = ItemType.SELECTABLE, dictEnName = DictConstants.AGENT_LEVEL)
  @Key
  DictConstants.AgentLevel levelName();

  /**
   * 佣金比例
   */
  @GenField(value = "佣金比例")
  BigDecimal rate();
}


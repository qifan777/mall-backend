package io.qifan.mall.server.agent.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.user.entity.User;
import org.babyfish.jimmer.sql.*;

import javax.validation.constraints.Null;

/**
 * Entity for table "agent"
 */
@Entity
@GenEntity
public interface Agent extends BaseEntity {

    @Null
    String tenantId();

    /**
     * 代理商编号
     */
    @GenField(value = "代理商编号")
    String agentNo();

    /**
     * 用户id
     */
    @IdView
    String userId();

    @GenField(value = "用户id")
    @OneToOne
    @Key
    User user();

    /**
     * 代理等级
     */
    @GenField(value = "代理等级", type = ItemType.SELECTABLE, dictEnName = DictConstants.AGENT_LEVEL_NAME)
    @ManyToOne
    AgentLevel agentLevel();

    @IdView
    String agentLevelId();

    /**
     * 上级代理id
     */
    String parentId();
}


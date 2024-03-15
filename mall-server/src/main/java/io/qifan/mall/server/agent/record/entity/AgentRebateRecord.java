package io.qifan.mall.server.agent.record.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.infrastructure.jimmer.BaseEntity;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToOne;

/**
 * Entity for table "agent_rebate_record"
 */
@Entity
@GenEntity
public interface AgentRebateRecord extends BaseEntity {

    /**
     * 代理人id
     */
    @GenField(value = "代理人")
    @IdView
    String agentId();

    @ManyToOne
    Agent agent();

    /**
     * 返佣订单号
     */
    @IdView
    @GenField(value = "返佣订单号")
    String productOrderId();

    @ManyToOne
    ProductOrder productOrder();


    /**
     * 钱包流水号
     */
    @IdView
    @GenField(value = "钱包流水号")
    String walletRecordId();

    @OneToOne
    WalletRecord walletRecord();

    /**
     * 来自第n级的返佣
     */
    @GenField(value = "来自第n级的返佣", type = ItemType.SELECTABLE, dictEnName = DictConstants.AGENT_LEVEL_NAME)
    DictConstants.AgentLevelName fromLevelName();

    /**
     * 返佣者
     */
    @IdView
    @GenField(value = "返佣者")
    String fromAgentId();

    @ManyToOne
    Agent fromAgent();
}


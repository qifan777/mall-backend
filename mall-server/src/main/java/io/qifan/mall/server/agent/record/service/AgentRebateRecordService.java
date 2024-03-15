package io.qifan.mall.server.agent.record.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.AgentLevelTable;
import io.qifan.mall.server.agent.level.repository.AgentLevelRepository;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecordDraft;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordInput;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec;
import io.qifan.mall.server.agent.record.repository.AgentRebateRecordRepository;
import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.AgentTable;
import io.qifan.mall.server.agent.root.repository.AgentRepository;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.dict.model.DictConstants.WalletRecordType;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.event.OrderEvent.OrderSuccessEvent;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import io.qifan.mall.server.wallet.record.entity.WalletRecordDraft;
import io.qifan.mall.server.wallet.record.service.WalletRecordService;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AgentRebateRecordService {

    private final AgentLevelRepository agentLevelRepository;
    private final AgentRebateRecordRepository agentRebateRecordRepository;
    private final AgentRepository agentRepository;
    private final WalletRecordService walletRecordService;

    public AgentRebateRecord findById(String id) {
        return agentRebateRecordRepository.findById(id, AgentRebateRecordRepository.COMPLEX_FETCHER)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(AgentRebateRecordInput agentRebateRecordInput) {
        return agentRebateRecordRepository.save(agentRebateRecordInput).id();
    }

    public Page<AgentRebateRecord> query(QueryRequest<AgentRebateRecordSpec> queryRequest) {
        return agentRebateRecordRepository.findPage(queryRequest,
                AgentRebateRecordRepository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        agentRebateRecordRepository.deleteAllById(ids);
        return true;
    }

    public @Null Agent findParentAgent(String userId) {
        AgentTable t = AgentTable.$;
        Agent agent = agentRepository.sql().createQuery(t)
                .where(t.userId().eq(userId))
                .select(t)
                .fetchOptional()
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "用户信息不券"));
        if (StringUtils.hasText(agent.parentId())) {
            return agentRepository.findById(agent.parentId(), AgentRepository.COMPLEX_FETCHER)
                    .orElse(null);
        }
        return null;
    }

    public void rebate(OrderSuccessEvent orderSuccessEvent) {
        Agent agent1, agent2, agent3;
        agent1 = findParentAgent(StpUtil.getLoginIdAsString());
        if (agent1 == null) {
            return;
        }
        rebate(orderSuccessEvent.productOrder(), agent1, DictConstants.AgentLevelName.FIRST);

        agent2 = findParentAgent(agent1.userId());
        if (agent2 == null) {
            return;
        }
        rebate(orderSuccessEvent.productOrder(), agent2, DictConstants.AgentLevelName.SECOND);

        agent3 = findParentAgent(agent2.userId());
        if (agent3 == null) {
            return;
        }
        rebate(orderSuccessEvent.productOrder(), agent3, DictConstants.AgentLevelName.THIRD);

    }


    public void rebate(ProductOrder productOrder, Agent agent,
                       DictConstants.AgentLevelName currentLevelName) {
        if (agent.agentLevel().levelName().getKeyId() < currentLevelName.getKeyId()) {
            return;
        }
        AgentLevelTable levelTable = AgentLevelTable.$;
        AgentLevel agentLevel = agentLevelRepository.sql().createQuery(levelTable)
                .where(levelTable.levelName().eq(currentLevelName))
                .select(levelTable)
                .fetchOptional()
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "代理等级不存在"));

        // 记录钱包流水
        WalletRecord walletRecord = walletRecordService.create(WalletRecordDraft.$.produce(draft -> {
            draft.setAmount(productOrder.payment().payAmount().multiply(agentLevel.rate()))
                    .setDescription("返佣")
                    .setType(WalletRecordType.REBATE)
            ;
        }), agent.userId());
        // 记录返佣流水
        AgentRebateRecord agentRebateRecord = AgentRebateRecordDraft.$.produce(draft -> {
            draft.setAgentId(agent.id())
                    .setProductOrderId(productOrder.id())
                    .setFromLevelName(currentLevelName)
                    .setWalletRecordId(walletRecord.id());
        });
        // 保存返佣记录
        agentRebateRecordRepository.save(agentRebateRecord);
    }

}
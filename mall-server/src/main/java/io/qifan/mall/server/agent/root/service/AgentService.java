package io.qifan.mall.server.agent.root.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.AgentLevelTable;
import io.qifan.mall.server.agent.level.repository.AgentLevelRepository;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecordDraft;
import io.qifan.mall.server.agent.record.repository.AgentRebateRecordRepository;
import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.AgentDraft;
import io.qifan.mall.server.agent.root.entity.AgentTable;
import io.qifan.mall.server.agent.root.entity.dto.AgentInput;
import io.qifan.mall.server.agent.root.entity.dto.AgentSpec;
import io.qifan.mall.server.agent.root.repository.AgentRepository;
import io.qifan.mall.server.coupon.user.event.UserEvent.UserCreateEvent;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.dict.model.DictConstants.WalletRecordType;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.entity.ProductOrder;
import io.qifan.mall.server.order.event.OrderEvent.OrderSuccessEvent;
import io.qifan.mall.server.wallet.root.entity.WalletTable;
import io.qifan.mall.server.wallet.root.repository.WalletRepository;
import jakarta.validation.constraints.Null;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AgentService {

  private final AgentRepository agentRepository;
  private final AgentLevelRepository agentLevelRepository;
  private final WalletRepository walletRepository;
  private final AgentRebateRecordRepository agentRebateRecordRepository;

  public Agent findById(String id) {
    return agentRepository.findById(id, AgentRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(AgentInput agentInput) {
    return agentRepository.save(agentInput).id();
  }

  public Page<Agent> query(QueryRequest<AgentSpec> queryRequest) {
    return agentRepository.findPage(queryRequest, AgentRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    agentRepository.deleteAllById(ids);
    return true;
  }

  private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz"; // 邀请码字符池

  public String generateUniqueInvitationCode() {
    Random random = new Random();
    // 为了保证唯一性，在实际应用中可能需要结合数据库来存储已生成的邀请码，并在此处进行校验避免重复
    while (true) {
      char[] invitationCodeChars = new char[8];
      for (int i = 0; i < 8; i++) {
        invitationCodeChars[i] = CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length()));
      }

      String invitationCode = new String(invitationCodeChars);

      // 假设 checkIfUnique 是一个方法用来检查生成的邀请码是否已经在系统中存在
      if (checkIfUnique(invitationCode)) {
        return invitationCode;
      }
    }
  }

  public boolean checkIfUnique(String code) {
    AgentTable t = AgentTable.$;
    return agentRepository.sql().createQuery(t)
        .where(t.agentNo().eq(code))
        .select(t.count())
        .fetchOne() == 0;
  }

  public void invite(UserCreateEvent userCreateEvent) {
    AgentTable t = AgentTable.$;
    AgentLevelTable t2 = AgentLevelTable.$;
    AgentLevel agentLevel = agentLevelRepository.sql().createQuery(t2)
        .where(t2.name().eq(DictConstants.AgentLevel.THIRD))
        .select(t2).fetchOptional()
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "代理等级不存在"));
    AgentDraft.$.produce(draft -> {
      draft.setAgentNo(generateUniqueInvitationCode())
          .setUserId(userCreateEvent.userId())
          .setLevel(agentLevel);
      if (StringUtils.hasText(userCreateEvent.inviteCode())) {
        Agent agent = agentRepository.sql().createQuery(t)
            .where(t.agentNo().eq(userCreateEvent.inviteCode())).select(t)
            .fetchOptional()
            .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "邀请码不正确"));
        draft.setParentId(agent.id());
      }
    });
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
    rebate(orderSuccessEvent.productOrder(), agent1, 0);

    agent2 = findParentAgent(agent1.userId());
    if (agent2 == null) {
      return;
    }
    rebate(orderSuccessEvent.productOrder(), agent2, 1);

    agent3 = findParentAgent(agent2.userId());
    if (agent3 == null) {
      return;
    }
    rebate(orderSuccessEvent.productOrder(), agent2, 3);

  }

  public void rebate(ProductOrder productOrder, Agent agent,
      Integer currentLevel) {
    if (agent.level().name().getKeyId() <= currentLevel) {
      AgentRebateRecord agentRebateRecord = AgentRebateRecordDraft.$.produce(draft -> {
        // 记录钱包流水
        draft.applyWalletRecord(walletDraft -> {
          walletDraft.setAmount(productOrder.payment().payAmount().multiply(agent.level().rate()))
              .setDescription("返佣")
              .setType(WalletRecordType.REBATE)
              .setId(IdUtil.fastSimpleUUID())
          ;
        });
        draft.setAgent(agent)
            .setProductOrder(productOrder);
      });
      // 保存返佣记录
      agentRebateRecordRepository.save(agentRebateRecord);
      // 更新钱包余额
      WalletTable t = WalletTable.$;
      walletRepository.sql().createUpdate(t)
          .where(t.userId().eq(agent.userId()))
          .set(t.balance(), t.balance().plus(agentRebateRecord.walletRecord().amount()));
    }
  }
}
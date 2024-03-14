package io.qifan.mall.server.agent.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.AgentLevelTable;
import io.qifan.mall.server.agent.level.repository.AgentLevelRepository;
import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.AgentDraft;
import io.qifan.mall.server.agent.root.entity.AgentTable;
import io.qifan.mall.server.agent.root.entity.dto.AgentInput;
import io.qifan.mall.server.agent.root.entity.dto.AgentSpec;
import io.qifan.mall.server.agent.root.repository.AgentRepository;
import io.qifan.mall.server.coupon.user.event.UserEvent.UserCreateEvent;
import io.qifan.mall.server.dict.model.DictConstants;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
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
        .where(t2.levelName().eq(DictConstants.AgentLevel.FIRST))
        .select(t2).fetchOptional()
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "代理等级不存在"));
    Agent produce = AgentDraft.$.produce(draft -> {
      draft.setAgentNo(generateUniqueInvitationCode())
          .setUserId(userCreateEvent.userId())
          .setAgentLevel(agentLevel);
      if (StringUtils.hasText(userCreateEvent.inviteCode())) {
        Agent agent = agentRepository.sql().createQuery(t)
            .where(t.agentNo().eq(userCreateEvent.inviteCode())).select(t)
            .fetchOptional()
            .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "邀请码不正确"));
        draft.setParentId(agent.id());
      }
    });
    agentRepository.save(produce);
  }
}
package io.qifan.mall.server.agent.level.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.dto.AgentLevelInput;
import io.qifan.mall.server.agent.level.entity.dto.AgentLevelSpec;
import io.qifan.mall.server.agent.level.repository.AgentLevelRepository;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AgentLevelService {

  private final AgentLevelRepository agentLevelRepository;

  public AgentLevel findById(String id) {
    return agentLevelRepository.findById(id, AgentLevelRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(AgentLevelInput agentLevelInput) {
    return agentLevelRepository.save(agentLevelInput).id();
  }

  public Page<AgentLevel> query(QueryRequest<AgentLevelSpec> queryRequest) {
    return agentLevelRepository.findPage(queryRequest, AgentLevelRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    agentLevelRepository.deleteAllById(ids);
    return true;
  }
}
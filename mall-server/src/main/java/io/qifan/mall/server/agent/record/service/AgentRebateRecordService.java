package io.qifan.mall.server.agent.record.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordInput;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec;
import io.qifan.mall.server.agent.record.repository.AgentRebateRecordRepository;
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
public class AgentRebateRecordService {

  private final AgentRebateRecordRepository agentRebateRecordRepository;

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
}
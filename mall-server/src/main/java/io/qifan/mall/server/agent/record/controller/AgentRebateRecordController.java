package io.qifan.mall.server.agent.record.controller;

import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordInput;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec;
import io.qifan.mall.server.agent.record.repository.AgentRebateRecordRepository;
import io.qifan.mall.server.agent.record.service.AgentRebateRecordService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agentRebateRecord")
@AllArgsConstructor
@DefaultFetcherOwner(AgentRebateRecordRepository.class)
public class AgentRebateRecordController {

  private final AgentRebateRecordService agentRebateRecordService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") AgentRebateRecord findById(@PathVariable String id) {
    return agentRebateRecordService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") AgentRebateRecord> query(
      @RequestBody QueryRequest<AgentRebateRecordSpec> queryRequest) {
    return agentRebateRecordService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated AgentRebateRecordInput agentRebateRecord) {
    return agentRebateRecordService.save(agentRebateRecord);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return agentRebateRecordService.delete(ids);
  }
}
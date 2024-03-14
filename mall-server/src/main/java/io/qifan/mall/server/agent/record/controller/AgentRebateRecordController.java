package io.qifan.mall.server.agent.record.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.mall.server.agent.record.entity.AgentRebateRecord;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordInput;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec;
import io.qifan.mall.server.agent.record.entity.dto.AgentRebateRecordSpec.TargetOf_agent;
import io.qifan.mall.server.agent.record.repository.AgentRebateRecordRepository;
import io.qifan.mall.server.agent.record.service.AgentRebateRecordService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.event.OrderEvent.OrderSuccessEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.context.event.EventListener;
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

  @PostMapping("today")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") AgentRebateRecord> today(
      @RequestBody QueryRequest<AgentRebateRecordSpec> queryRequest) {
    TargetOf_agent targetOfAgent = new TargetOf_agent();
    targetOfAgent.setUserId(StpUtil.getLoginIdAsString());
    queryRequest.getQuery().setAgent(targetOfAgent);
    queryRequest.getQuery()
        .setMaxCreatedTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0)));
    queryRequest.getQuery()
        .setMinCreatedTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)));
    return agentRebateRecordService.query(queryRequest);
  }

  @EventListener
  public void onOrderSuccessEvent(OrderSuccessEvent orderSuccessEvent) {
    agentRebateRecordService.rebate(orderSuccessEvent);
  }
}
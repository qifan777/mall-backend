package io.qifan.mall.server.agent.root.controller;

import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.dto.AgentInput;
import io.qifan.mall.server.agent.root.entity.dto.AgentSpec;
import io.qifan.mall.server.agent.root.repository.AgentRepository;
import io.qifan.mall.server.agent.root.service.AgentService;
import io.qifan.mall.server.coupon.user.event.UserEvent.UserCreateEvent;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.order.event.OrderEvent.OrderSuccessEvent;
import io.qifan.mall.server.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.event.EntityEvent;
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
@RequestMapping("agent")
@AllArgsConstructor
@DefaultFetcherOwner(AgentRepository.class)
public class AgentController {

  private final AgentService agentService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") Agent findById(@PathVariable String id) {
    return agentService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") Agent> query(
      @RequestBody QueryRequest<AgentSpec> queryRequest) {
    return agentService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated AgentInput agent) {
    return agentService.save(agent);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return agentService.delete(ids);
  }
  @EventListener
  public void onUserCreatedEvent(UserCreateEvent userCreateEvent) {
    agentService.invite(userCreateEvent);
  }
  @EventListener
  public void onOrderSuccessEvent(OrderSuccessEvent orderSuccessEvent){
    agentService.rebate(orderSuccessEvent);
  }
}
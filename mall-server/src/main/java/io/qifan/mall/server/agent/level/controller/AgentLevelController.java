package io.qifan.mall.server.agent.level.controller;

import io.qifan.mall.server.agent.level.entity.AgentLevel;
import io.qifan.mall.server.agent.level.entity.dto.AgentLevelInput;
import io.qifan.mall.server.agent.level.entity.dto.AgentLevelSpec;
import io.qifan.mall.server.agent.level.repository.AgentLevelRepository;
import io.qifan.mall.server.agent.level.service.AgentLevelService;
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
@RequestMapping("agentLevel")
@AllArgsConstructor
@DefaultFetcherOwner(AgentLevelRepository.class)
public class AgentLevelController {

  private final AgentLevelService agentLevelService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") AgentLevel findById(@PathVariable String id) {
    return agentLevelService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") AgentLevel> query(
      @RequestBody QueryRequest<AgentLevelSpec> queryRequest) {
    return agentLevelService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated AgentLevelInput agentLevel) {
    return agentLevelService.save(agentLevel);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return agentLevelService.delete(ids);
  }

}
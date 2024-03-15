package io.qifan.mall.server.agent.root.controller;

import io.qifan.mall.server.agent.root.entity.Agent;
import io.qifan.mall.server.agent.root.entity.dto.AgentInput;
import io.qifan.mall.server.agent.root.entity.dto.AgentSpec;
import io.qifan.mall.server.agent.root.repository.AgentRepository;
import io.qifan.mall.server.agent.root.service.AgentService;
import io.qifan.mall.server.coupon.user.event.UserEvent.UserCreateEvent;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("qr-code")
    public byte[] qrCode() {
        return agentService.qrCode();
    }

    @EventListener
    public void onUserCreatedEvent(UserCreateEvent userCreateEvent) {
        agentService.invite(userCreateEvent);
    }
}
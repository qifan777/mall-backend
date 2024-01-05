package io.qifan.mall.server.inviter.history.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.history.entity.InviteHistory;
import io.qifan.mall.server.inviter.history.entity.dto.InviteHistorySpec;
import io.qifan.mall.server.inviter.history.repository.InviteHistoryRepository;
import io.qifan.mall.server.inviter.history.service.InviteHistoryService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inviteHistory")
@AllArgsConstructor
public class InviteHistoryController {
    private final InviteHistoryService inviteHistoryService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = InviteHistoryRepository.class) InviteHistory findById(@PathVariable String id) {
        return inviteHistoryService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = InviteHistoryRepository.class) InviteHistory> query(@RequestBody QueryRequest<InviteHistorySpec> queryRequest) {
        return inviteHistoryService.query(queryRequest);
    }

    @PostMapping("user")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = InviteHistoryRepository.class) InviteHistory> userInviteHistory(@RequestBody QueryRequest<InviteHistorySpec> queryRequest) {
        return inviteHistoryService.userInviteHistory(queryRequest);
    }

    @PostMapping("check")
    public Boolean check(@RequestParam String code) {
        return inviteHistoryService.check(code);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return inviteHistoryService.delete(ids);
    }
}
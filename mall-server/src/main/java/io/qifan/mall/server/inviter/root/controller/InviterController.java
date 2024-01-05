package io.qifan.mall.server.inviter.root.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.inviter.root.entity.Inviter;
import io.qifan.mall.server.inviter.root.entity.dto.InviterInput;
import io.qifan.mall.server.inviter.root.entity.dto.InviterSpec;
import io.qifan.mall.server.inviter.root.repository.InviterRepository;
import io.qifan.mall.server.inviter.root.service.InviterService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inviter")
@AllArgsConstructor
public class InviterController {
    private final InviterService inviterService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = InviterRepository.class) Inviter findById(@PathVariable String id) {
        return inviterService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = InviterRepository.class) Inviter> query(@RequestBody QueryRequest<InviterSpec> queryRequest) {
        return inviterService.query(queryRequest);
    }

    @PostMapping("save")
    public String save() {
        return inviterService.save();
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return inviterService.delete(ids);
    }
    @GetMapping("info")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = InviterRepository.class) Inviter getInviteInfo(){
        return inviterService.getInviteInfo();
    }
}
package io.qifan.mall.server.role.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.role.entity.Role;
import io.qifan.mall.server.role.entity.dto.RoleInput;
import io.qifan.mall.server.role.entity.dto.RoleSpec;
import io.qifan.mall.server.role.repository.RoleRepository;
import io.qifan.mall.server.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = RoleRepository.class) Role findById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = RoleRepository.class) Role> query(@RequestBody QueryRequest<RoleSpec> queryRequest) {
        return roleService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody RoleInput role) {
        return roleService.save(role);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return roleService.delete(ids);
    }
}
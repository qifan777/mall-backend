package io.qifan.mall.server.role.controller;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.role.entity.Role;
import io.qifan.mall.server.role.entity.dto.RoleInput;
import io.qifan.mall.server.role.entity.dto.RoleSpec;
import io.qifan.mall.server.role.repository.RoleRepository;
import io.qifan.mall.server.role.service.RoleService;
import io.qifan.mall.server.user.repository.UserRepository;
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
@RequestMapping("role")
@AllArgsConstructor
@DefaultFetcherOwner(RoleRepository.class)
public class RoleController {
    private final RoleService roleService;

    @GetMapping("{id}")
    public @FetchBy(value = "ROLE_MENU_FETCHER") Role findById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER") Role> query(@RequestBody QueryRequest<RoleSpec> queryRequest) {
        return roleService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated RoleInput role) {
        return roleService.save(role);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody List<String> ids) {
        return roleService.delete(ids);
    }
}
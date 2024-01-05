package io.qifan.mall.server.menu.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.menu.entity.Menu;
import io.qifan.mall.server.menu.entity.dto.MenuInput;
import io.qifan.mall.server.menu.entity.dto.MenuSpec;
import io.qifan.mall.server.menu.repository.MenuRepository;
import io.qifan.mall.server.menu.service.MenuService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER", ownerType = MenuRepository.class) Menu findById(@PathVariable String id) {
        return menuService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = MenuRepository.class) Menu> query(@RequestBody QueryRequest<MenuSpec> queryRequest) {
        return menuService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody MenuInput menu) {
        return menuService.save(menu);
    }

    @PostMapping("delete")
    public boolean delete(@RequestBody List<String> ids) {
        return menuService.delete(ids);
    }

    @GetMapping("tree")
    public List<@FetchBy(value = "MENU_TREE_FETCHER", ownerType = MenuRepository.class) Menu> queryMenuTree() {
        return menuService.queryMenuTree();
    }

}
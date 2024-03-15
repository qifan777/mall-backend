package io.qifan.mall.server.wallet.root.controller;

import io.qifan.mall.server.coupon.user.event.UserEvent;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import io.qifan.mall.server.wallet.root.entity.Wallet;
import io.qifan.mall.server.wallet.root.entity.dto.WalletInput;
import io.qifan.mall.server.wallet.root.entity.dto.WalletSpec;
import io.qifan.mall.server.wallet.root.repository.WalletRepository;
import io.qifan.mall.server.wallet.root.service.WalletService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.event.EntityEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallet")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRepository.class)
@Slf4j
public class WalletController {

    private final WalletService walletService;
    private final JSqlClient jSqlClient;

    @PostConstruct
    public void init() {
        jSqlClient.getTriggers().addEntityListener(WalletRecord.class, e -> {
            if (e.getType().equals(EntityEvent.Type.INSERT)) {
                walletService.changeBalance(e);
            }
        });
    }

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER") Wallet findById(@PathVariable String id) {
        return walletService.findById(id);
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER") Wallet> query(
            @RequestBody QueryRequest<WalletSpec> queryRequest) {
        return walletService.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated WalletInput wallet) {
        return walletService.save(wallet);
    }

    @PostMapping("delete")
    public Boolean delete(@RequestBody List<String> ids) {
        return walletService.delete(ids);
    }

    @EventListener
    public void onUserCreatedEvent(UserEvent.UserCreateEvent userCreateEvent) {
        walletService.create(userCreateEvent.userId());
    }

//    @EventListener
//    public void onWalletRecordCreatedEvent(EntityEvent<WalletRecord> event) {
//        log.info("钱包发生变动：{}", event);
//        walletService.changeBalance(event);
//    }
}
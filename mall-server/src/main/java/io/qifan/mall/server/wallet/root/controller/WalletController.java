package io.qifan.mall.server.wallet.root.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.wallet.root.entity.Wallet;
import io.qifan.mall.server.wallet.root.entity.dto.WalletInput;
import io.qifan.mall.server.wallet.root.entity.dto.WalletSpec;
import io.qifan.mall.server.wallet.root.repository.WalletRepository;
import io.qifan.mall.server.wallet.root.service.WalletService;
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
@RequestMapping("wallet")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRepository.class)
public class WalletController {

  private final WalletService walletService;

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
}
package io.qifan.mall.server.wallet.record.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import io.qifan.mall.server.wallet.record.entity.dto.WalletRecordInput;
import io.qifan.mall.server.wallet.record.entity.dto.WalletRecordSpec;
import io.qifan.mall.server.wallet.record.repository.WalletRecordRepository;
import io.qifan.mall.server.wallet.record.service.WalletRecordService;
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
@RequestMapping("walletRecord")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRecordRepository.class)
public class WalletRecordController {

  private final WalletRecordService walletRecordService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") WalletRecord findById(@PathVariable String id) {
    return walletRecordService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") WalletRecord> query(
      @RequestBody QueryRequest<WalletRecordSpec> queryRequest) {
    return walletRecordService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated WalletRecordInput walletRecord) {
    return walletRecordService.save(walletRecord);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return walletRecordService.delete(ids);
  }
}
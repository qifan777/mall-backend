package io.qifan.mall.server.refund.controller;

import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.refund.entity.RefundRecord;
import io.qifan.mall.server.refund.entity.dto.RefundRecordInput;
import io.qifan.mall.server.refund.entity.dto.RefundRecordSpec;
import io.qifan.mall.server.refund.repository.RefundRecordRepository;
import io.qifan.mall.server.refund.service.RefundRecordService;
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
@RequestMapping("refundRecord")
@AllArgsConstructor
@DefaultFetcherOwner(RefundRecordRepository.class)
public class RefundRecordController {

  private final RefundRecordService refundRecordService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") RefundRecord findById(@PathVariable String id) {
    return refundRecordService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") RefundRecord> query(
      @RequestBody QueryRequest<RefundRecordSpec> queryRequest) {
    return refundRecordService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated RefundRecordInput refundRecord) {
    return refundRecordService.save(refundRecord);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return refundRecordService.delete(ids);
  }
}
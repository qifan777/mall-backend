package io.qifan.mall.server.coupon.use.controller;

import io.qifan.mall.server.coupon.use.entity.CouponUseRecord;
import io.qifan.mall.server.coupon.use.entity.dto.CouponUseRecordInput;
import io.qifan.mall.server.coupon.use.entity.dto.CouponUseRecordSpec;
import io.qifan.mall.server.coupon.use.repository.CouponUseRecordRepository;
import io.qifan.mall.server.coupon.use.service.CouponUseRecordService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("couponUseRecord")
@AllArgsConstructor
public class CouponUseRecordController {

  private final CouponUseRecordService couponUseRecordService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = CouponUseRecordRepository.class) CouponUseRecord findById(
      @PathVariable String id) {
    return couponUseRecordService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = CouponUseRecordRepository.class) CouponUseRecord> query(
      @RequestBody QueryRequest<CouponUseRecordSpec> queryRequest) {
    return couponUseRecordService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated CouponUseRecordInput couponUseRecord) {
    return couponUseRecordService.save(couponUseRecord);
  }

  @PostMapping("delete")
  public boolean delete(@RequestBody List<String> ids) {
    return couponUseRecordService.delete(ids);
  }
}
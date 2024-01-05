package io.qifan.mall.server.coupon.root.controller;

import io.qifan.mall.server.coupon.root.entity.Coupon;
import io.qifan.mall.server.coupon.root.entity.dto.CouponInput;
import io.qifan.mall.server.coupon.root.entity.dto.CouponSpec;
import io.qifan.mall.server.coupon.root.repository.CouponRepository;
import io.qifan.mall.server.coupon.root.service.CouponService;
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
@RequestMapping("coupon")
@AllArgsConstructor
public class CouponController {

  private final CouponService couponService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = CouponRepository.class) Coupon findById(
      @PathVariable String id) {
    return couponService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = CouponRepository.class) Coupon> query(
      @RequestBody QueryRequest<CouponSpec> queryRequest) {
    return couponService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated CouponInput coupon) {
    return couponService.save(coupon);
  }

  @PostMapping("delete")
  public boolean delete(@RequestBody List<String> ids) {
    return couponService.delete(ids);
  }
}
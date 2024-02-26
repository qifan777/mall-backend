package io.qifan.mall.server.coupon.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserInput;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserSpec;
import io.qifan.mall.server.coupon.user.model.GiftCouponInput;
import io.qifan.mall.server.coupon.user.repository.CouponUserRepository;
import io.qifan.mall.server.coupon.user.service.CouponUserService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
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
@RequestMapping("couponUser")
@AllArgsConstructor
@DefaultFetcherOwner(CouponUserRepository.class)
public class CouponUserController {

  private final CouponUserService couponUserService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") CouponUser findById(@PathVariable String id) {
    return couponUserService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") CouponUser> query(
      @RequestBody QueryRequest<CouponUserSpec> queryRequest) {
    return couponUserService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated CouponUserInput couponUser) {
    return couponUserService.save(couponUser);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return couponUserService.delete(ids);
  }

  @PostMapping("gift")
  public boolean gift(@RequestBody @Validated GiftCouponInput giftCouponInput) {
    return couponUserService.gift(giftCouponInput);
  }

  @PostMapping("user")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") CouponUser> queryByUser(@RequestBody QueryRequest<CouponUserSpec> queryRequest) {
    queryRequest.getQuery().setUserId(StpUtil.getLoginIdAsString());
    return couponUserService.query(queryRequest);
  }
}
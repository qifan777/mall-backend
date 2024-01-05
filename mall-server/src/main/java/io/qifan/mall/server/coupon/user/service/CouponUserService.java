package io.qifan.mall.server.coupon.user.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.coupon.root.service.CouponService;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.entity.CouponUserDraft;
import io.qifan.mall.server.coupon.user.entity.CouponUserTable;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserInput;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserSpec;
import io.qifan.mall.server.coupon.user.model.GiftCouponInput;
import io.qifan.mall.server.coupon.user.repository.CouponUserRepository;
import io.qifan.mall.server.dict.model.DictConstants.CouponReceiveType;
import io.qifan.mall.server.dict.model.DictConstants.CouponUseStatus;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponUserService {

  private final CouponUserRepository couponUserRepository;
  private final CouponService couponService;

  public CouponUser findById(String id) {
    return couponUserRepository.findById(id, CouponUserRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(CouponUserInput couponUserInput) {
    return couponUserRepository.save(couponUserInput).id();
  }

  public Page<CouponUser> query(QueryRequest<CouponUserSpec> queryRequest) {
    return couponUserRepository.findPage(queryRequest, CouponUserRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    couponUserRepository.deleteAllById(ids);
    return true;
  }

  public boolean gift(GiftCouponInput giftCoupon) {
    couponService.receiveCheck(giftCoupon.getCouponId());
    List<CouponUser> couponUsers = giftCoupon.getUserIds().stream()
        .map(userId -> CouponUserDraft.$.produce(draft -> {
          draft.setCouponId(giftCoupon.getCouponId())
              .setUserId(userId)
              .setReceiveType(CouponReceiveType.SYSTEM_GIFT)
              .setStatus(CouponUseStatus.UNUSED);
        })).collect(Collectors.toList());
    couponUserRepository.saveAll(couponUsers);
    return true;
  }

  public List<CouponUser> availableCoupons(BigDecimal productPrice) {
    CouponUserTable couponUserTable = CouponUserTable.$;
    return couponUserRepository.sql().createQuery(couponUserTable)
        .where(couponUserTable.coupon().thresholdAmount().le(productPrice))
        .where(couponUserTable.coupon().effectiveDate().le(LocalDateTime.now()))
        .where(couponUserTable.coupon().expirationDate().gt(LocalDateTime.now()))
        .where(couponUserTable.userId().eq(StpUtil.getLoginIdAsString()))
        .select(couponUserTable)
        .execute();
  }

  public BigDecimal calculate(String id, BigDecimal productPrice) {
    CouponUserTable couponUserTable = CouponUserTable.$;
    CouponUser couponUser = couponUserRepository.sql()
        .createQuery(couponUserTable)
        .where(couponUserTable.id().eq(id))
        .where(couponUserTable.userId().eq(StpUtil.getLoginIdAsString()))
        .select(couponUserTable)
        .fetchOptional()
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券不存在"));
    return couponService.calculate(couponUser.couponId(), productPrice);
  }

  public List<CouponUser> listByIds(List<String> ids) {
    CouponUserTable couponUserTable = CouponUserTable.$;
    return couponUserRepository.sql()
        .createQuery(couponUserTable)
        .where(couponUserTable.userId().eq(StpUtil.getLoginIdAsString()))
        .where(couponUserTable.id().in(ids))
        .select(couponUserTable.fetch(CouponUserRepository.COMPLEX_FETCHER))
        .execute();

  }
}
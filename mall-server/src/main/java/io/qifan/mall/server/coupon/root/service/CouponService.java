package io.qifan.mall.server.coupon.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.coupon.root.entity.Coupon;
import io.qifan.mall.server.coupon.root.entity.dto.CouponInput;
import io.qifan.mall.server.coupon.root.entity.dto.CouponSpec;
import io.qifan.mall.server.coupon.root.repository.CouponRepository;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponService {

  private final CouponRepository couponRepository;

  public Coupon findById(String id) {
    return couponRepository.findById(id, CouponRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(
            ResultCode.NotFindError, "数据不存在"));
  }

  public String save(CouponInput couponInput) {
    return couponRepository.save(couponInput).id();
  }

  public Page<Coupon> query(QueryRequest<CouponSpec> queryRequest) {
    return couponRepository.findPage(queryRequest, CouponRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    couponRepository.deleteAllById(ids);
    return true;
  }

  public void countCheck(String id) {
    Coupon coupon = couponRepository.findById(id)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券不存在"));
    if (coupon.releasedQuantity() == 0) {
      throw new BusinessException(ResultCode.NotFindError, "优惠券已领完");
    }
  }
}
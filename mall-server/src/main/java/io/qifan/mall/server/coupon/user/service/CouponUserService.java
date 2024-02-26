package io.qifan.mall.server.coupon.user.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.coupon.user.entity.CouponUser;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserInput;
import io.qifan.mall.server.coupon.user.entity.dto.CouponUserSpec;
import io.qifan.mall.server.coupon.user.repository.CouponUserRepository;
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
public class CouponUserService {

  private final CouponUserRepository couponUserRepository;

  public CouponUser findById(String id) {
    return couponUserRepository.findById(id, CouponUserRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(
            ResultCode.NotFindError, "数据不存在"));
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
}
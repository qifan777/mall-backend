package io.qifan.mall.server.coupon.use.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.coupon.use.entity.CouponUseRecord;
import io.qifan.mall.server.coupon.use.entity.dto.CouponUseRecordInput;
import io.qifan.mall.server.coupon.use.entity.dto.CouponUseRecordSpec;
import io.qifan.mall.server.coupon.use.repository.CouponUseRecordRepository;
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
public class CouponUseRecordService {

  private final CouponUseRecordRepository couponUseRecordRepository;

  public CouponUseRecord findById(String id) {
    return couponUseRecordRepository.findById(id, CouponUseRecordRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(CouponUseRecordInput couponUseRecordInput) {
    return couponUseRecordRepository.save(couponUseRecordInput).id();
  }

  public Page<CouponUseRecord> query(QueryRequest<CouponUseRecordSpec> queryRequest) {
    return couponUseRecordRepository.findPage(queryRequest,
        CouponUseRecordRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    couponUseRecordRepository.deleteAllById(ids);
    return true;
  }
}
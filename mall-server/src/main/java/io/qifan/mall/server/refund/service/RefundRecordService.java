package io.qifan.mall.server.refund.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.refund.entity.RefundRecord;
import io.qifan.mall.server.refund.entity.dto.RefundRecordInput;
import io.qifan.mall.server.refund.entity.dto.RefundRecordSpec;
import io.qifan.mall.server.refund.repository.RefundRecordRepository;
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
public class RefundRecordService {

  private final RefundRecordRepository refundRecordRepository;

  public RefundRecord findById(String id) {
    return refundRecordRepository.findById(id, RefundRecordRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(RefundRecordInput refundRecordInput) {
    return refundRecordRepository.save(refundRecordInput).id();
  }

  public Page<RefundRecord> query(QueryRequest<RefundRecordSpec> queryRequest) {
    return refundRecordRepository.findPage(queryRequest, RefundRecordRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    refundRecordRepository.deleteAllById(ids);
    return true;
  }
}
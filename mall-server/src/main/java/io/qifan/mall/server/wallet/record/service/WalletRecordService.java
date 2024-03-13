package io.qifan.mall.server.wallet.record.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.wallet.record.entity.WalletRecord;
import io.qifan.mall.server.wallet.record.entity.dto.WalletRecordInput;
import io.qifan.mall.server.wallet.record.entity.dto.WalletRecordSpec;
import io.qifan.mall.server.wallet.record.repository.WalletRecordRepository;
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
public class WalletRecordService {

  private final WalletRecordRepository walletRecordRepository;

  public WalletRecord findById(String id) {
    return walletRecordRepository.findById(id, WalletRecordRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(WalletRecordInput walletRecordInput) {
    return walletRecordRepository.save(walletRecordInput).id();
  }

  public Page<WalletRecord> query(QueryRequest<WalletRecordSpec> queryRequest) {
    return walletRecordRepository.findPage(queryRequest, WalletRecordRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    walletRecordRepository.deleteAllById(ids);
    return true;
  }
}
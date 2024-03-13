package io.qifan.mall.server.wallet.root.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.wallet.root.entity.Wallet;
import io.qifan.mall.server.wallet.root.entity.dto.WalletInput;
import io.qifan.mall.server.wallet.root.entity.dto.WalletSpec;
import io.qifan.mall.server.wallet.root.repository.WalletRepository;
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
public class WalletService {

  private final WalletRepository walletRepository;

  public Wallet findById(String id) {
    return walletRepository.findById(id, WalletRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(WalletInput walletInput) {
    return walletRepository.save(walletInput).id();
  }

  public Page<Wallet> query(QueryRequest<WalletSpec> queryRequest) {
    return walletRepository.findPage(queryRequest, WalletRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    walletRepository.deleteAllById(ids);
    return true;
  }
}
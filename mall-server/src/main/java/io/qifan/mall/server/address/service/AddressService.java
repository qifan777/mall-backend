package io.qifan.mall.server.address.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.mall.server.address.entity.Address;
import io.qifan.mall.server.address.entity.AddressDraft;
import io.qifan.mall.server.address.entity.AddressTable;
import io.qifan.mall.server.address.entity.dto.AddressInput;
import io.qifan.mall.server.address.entity.dto.AddressSpec;
import io.qifan.mall.server.address.repository.AddressRepository;
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
public class AddressService {

  private final AddressRepository addressRepository;

  public Address findById(String id) {
    return addressRepository.findById(id, AddressRepository.COMPLEX_FETCHER)
        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
  }

  public String save(AddressInput addressInput) {
    return addressRepository.save(addressInput).id();
  }

  public Page<Address> query(QueryRequest<AddressSpec> queryRequest) {
    return addressRepository.findPage(queryRequest, AddressRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    addressRepository.deleteAllById(ids);
    return true;
  }

  public List<Address> getUserAddress() {
    AddressTable t = AddressTable.$;
    return addressRepository.sql().createQuery(t)
        .where(t.creator().id().eq(StpUtil.getLoginIdAsString()))
        .orderBy(t.top().desc())
        .select(t.fetch(AddressRepository.SIMPLE_FETCHER))
        .execute();
  }

  public Boolean top(String id) {
    AddressTable t = AddressTable.$;
    // 设置该用户的其他地址为非默认
    addressRepository.sql().createUpdate(t)
        .set(t.top(), false)
        .where(t.creator().id().eq(StpUtil.getLoginIdAsString()))
        .execute();
    // 设置传入的地址为默认
    addressRepository.update(AddressDraft.$.produce(draft -> draft.setId(id)
        .setTop(true)));
    return true;
  }
}
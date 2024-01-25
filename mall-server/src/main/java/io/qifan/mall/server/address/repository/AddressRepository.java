package io.qifan.mall.server.address.repository;

import io.qifan.mall.server.address.entity.Address;
import io.qifan.mall.server.address.entity.AddressFetcher;
import io.qifan.mall.server.address.entity.AddressTable;
import io.qifan.mall.server.address.entity.dto.AddressSpec;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import io.qifan.mall.server.user.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepository extends JRepository<Address, String> {

  AddressTable addressTable = AddressTable.$;
  AddressFetcher COMPLEX_FETCHER = AddressFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  AddressFetcher SIMPLE_FETCHER = AddressFetcher.$.allScalarFields();;

  default Page<Address> findPage(QueryRequest<AddressSpec> queryRequest, Fetcher<Address> fetcher) {
    AddressSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return pager(pageable).execute(sql().createQuery(addressTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(addressTable, pageable.getSort()))
        .select(addressTable.fetch(fetcher)));
  }
}
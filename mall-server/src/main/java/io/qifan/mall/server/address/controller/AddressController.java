package io.qifan.mall.server.address.controller;

import io.qifan.mall.server.address.entity.Address;
import io.qifan.mall.server.address.entity.dto.AddressInput;
import io.qifan.mall.server.address.entity.dto.AddressSpec;
import io.qifan.mall.server.address.repository.AddressRepository;
import io.qifan.mall.server.address.service.AddressService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("address")
@AllArgsConstructor
public class AddressController {

  private final AddressService addressService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = AddressRepository.class) Address findById(
      @PathVariable String id) {
    return addressService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = AddressRepository.class) Address> query(
      @RequestBody QueryRequest<AddressSpec> queryRequest) {
    return addressService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated AddressInput address) {
    return addressService.save(address);
  }

  @PostMapping("delete")
  public boolean delete(@RequestBody List<String> ids) {
    return addressService.delete(ids);
  }
}
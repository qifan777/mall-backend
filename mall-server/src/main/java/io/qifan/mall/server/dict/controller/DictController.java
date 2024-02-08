package io.qifan.mall.server.dict.controller;

import io.qifan.mall.server.dict.entity.Dict;
import io.qifan.mall.server.dict.entity.dto.DictInput;
import io.qifan.mall.server.dict.entity.dto.DictSpec;
import io.qifan.mall.server.dict.repository.DictRepository;
import io.qifan.mall.server.dict.service.DictService;
import io.qifan.mall.server.infrastructure.model.QueryRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dict")
@AllArgsConstructor
public class DictController {

  private final DictService dictService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER", ownerType = DictRepository.class) Dict findById(
      @PathVariable String id) {
    return dictService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER", ownerType = DictRepository.class) Dict> query(
      @RequestBody QueryRequest<DictSpec> queryRequest) {
    return dictService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated DictInput dict) {
    return dictService.save(dict);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return dictService.delete(ids);
  }

  @GetMapping("ts")
  public byte[] generateTS() {
    return dictService.generateTS().getBytes(StandardCharsets.UTF_8);
  }

  @GetMapping("java")
  public void generateJava() {
    dictService.generateJava();
  }
}
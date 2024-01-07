package io.qifan.mall.server.dict.model;

import io.qifan.mall.server.dict.entity.Dict;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DictGenContext {

  private List<String> dictTypes;
private Map<String, List<Dict>> dictMap;
}

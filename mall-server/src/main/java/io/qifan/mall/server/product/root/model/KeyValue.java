package io.qifan.mall.server.product.root.model;

import java.util.List;
import lombok.Data;

@Data
public class KeyValue {

  private String name;
  private List<String> values;
}
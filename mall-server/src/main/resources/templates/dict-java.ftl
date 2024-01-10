<#-- @ftlvariable name="" type="io.qifan.mall.server.dict.model.DictGenContext" -->
package io.qifan.mall.server.dict.model;
import lombok.Getter;
public class DictConstants {
<#list getDictTypes() as type>
  public static final String ${getDictMap()[type][0].dictEnName()} = "${getDictMap()[type][0].dictEnName()}";
</#list>
<#list getDictTypes() as type>
  @Getter
  public enum ${type}{
  <#list getDictMap()[type] as dict>
    ${dict.keyEnName()},
  </#list>
  ;
  }
</#list>
}

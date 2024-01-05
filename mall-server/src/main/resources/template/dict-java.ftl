<#-- @ftlvariable name="" type="io.qifan.mall.server.dict.model.DictGenContext" -->
public class DictConstants {
<#list getDictTypes() as type>
  public static class ${type}{
  <#list getDictMap()[type] as dict>
    public static final String ${dict.keyEnName()}="${dict.id()}";
  </#list>
  }
</#list>
}

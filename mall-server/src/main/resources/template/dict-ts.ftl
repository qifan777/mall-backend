<#-- @ftlvariable name="" type="io.qifan.mall.server.dict.model.DictGenContext" -->
export const DictConstants = {
<#list getDictTypes() as type>
    ${type}: {
    <#list getDictMap()[type] as dict >
        ${dict.keyEnName()}: '${dict.id()}',
    </#list>
  },
</#list>
}

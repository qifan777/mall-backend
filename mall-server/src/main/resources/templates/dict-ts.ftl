<#-- @ftlvariable name="" type="io.qifan.mall.server.dict.model.DictGenContext" -->
export const DictConstants = {
<#list getDictTypes() as type>
    ${getDictMap()[type][0].dictEnName()}: ${getDictMap()[type][0].dictId()?c},
</#list>
}

<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.dto.Dto" -->

input ${type.typeName}Input {
    #allScalars(${type.typeName})
    id?
}

specification ${type.typeName}Spec {
    #allScalars
    <#list getFields() as field>
    <#switch field.itemField.itemType.code>
    <#case 1>
    like/i(${field.itemField.fieldName})
    <#break>
    <#case 2>
    like/i(${field.itemField.fieldName})
    <#break>
    <#case 5>
    ge(${field.itemField.fieldName})
    le(${field.itemField.fieldName})
    </#switch>
    </#list>
}
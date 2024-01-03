<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.TableItem" -->
<#if getItemType().code!=-1>
    <el-table-column label="${getLabel()}" prop="${getBind()}" sortable="custom">
        <template v-slot:default="{row}:${entityType.typeName}Scope">
            <#switch getItemType().code>
                <#case 0>
                    <dict-column :dict-id="${getDictId()}" :value="row.${getBind()}"></dict-column>
                    <#break>
                <#case 4>
                    <el-avatar :src="row.${getBind()}" alt=""></el-avatar>
                    <#break>
                <#default>
                    {{row.${getBind()}}}
            </#switch>
        </template>
    </el-table-column>
</#if>

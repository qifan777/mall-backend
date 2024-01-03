<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.FormItem" -->
<#if getItemType().code!=-1 &&getItemType().code!=4>
    <el-form-item label="${getLabel()}">
        <#switch getItemType().code>
            <#case 0>
                <dict-select :dict-id="${getDictId()}" v-model="query.${getBind()}"></dict-select>
                <#break>
            <#case 3>
                <el-input-number v-model="query.${getBind()}" controls-position="right"></el-input-number>
                <#break>
            <#case 5>
                <datetime-picker
                    v-model:min-date-time="query.min${getBind()?cap_first}"
                    v-model:max-date-time="query.max${getBind()?cap_first}"
                >
                </datetime-picker>
                <#break>
            <#default>
                <el-input v-model="query.${getBind()}"></el-input>
        </#switch>
    </el-form-item>
</#if>
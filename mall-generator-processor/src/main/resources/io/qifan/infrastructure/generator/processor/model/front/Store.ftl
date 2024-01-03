<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Store" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { ${entityType.typeName}Input, ${entityType.typeName}Spec } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { ${entityType.typeName}Dto } from '@/apis/__generated/model/dto'

export const use${entityType.typeName}Store = defineStore('${uncapitalizeTypeName}', () => {
  const initQuery: ${entityType.typeName}Spec = {}
  const initForm: ${entityType.typeName}Input = {  }
  const tableHelper = useTableHelper(api.${uncapitalizeTypeName}Controller.query, api.${uncapitalizeTypeName}Controller, initQuery)
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<${entityType.typeName}Spec>(initQuery)
  const updateForm =ref<${entityType.typeName}Input>({...initForm})
  const createForm = ref<${entityType.typeName}Input>({...initForm})
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm}
})

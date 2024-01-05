<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.service.Service" -->
package ${type.packagePath};

<#list importTypes as importType>
    import ${importType.getTypePath()};
</#list>
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ${type.typeName} {
    private final ${entityType.typeName}Repository ${uncapitalizeTypeName}Repository;

    public ${entityType.typeName} findById(String id) {
        return ${uncapitalizeTypeName}Repository.findById(id, ${entityType.typeName}Repository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
    }

    public String save(${entityType.typeName}Input ${uncapitalizeTypeName}Input) {
        return ${uncapitalizeTypeName}Repository.save(${uncapitalizeTypeName}Input).id();
    }

    public Page<${entityType.typeName}> query(QueryRequest<${entityType.typeName}Spec> queryRequest) {
        return ${uncapitalizeTypeName}Repository.findPage(queryRequest, ${entityType.typeName}Repository.COMPLEX_FETCHER);
    }

    public boolean delete(List<String> ids) {
        ${uncapitalizeTypeName}Repository.deleteAllById(ids);
        return true;
    }
}
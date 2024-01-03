<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.repository.Repository" -->
package ${type.packagePath};

<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

public interface ${type.typeName} extends JRepository<${entityType.typeName}, String> {
    ${entityType.typeName}Table ${uncapitalizeTypeName}Table = ${entityType.typeName}Table.$;
    ${entityType.typeName}Fetcher COMPLEX_FETCHER = ${entityType.typeName}Fetcher.$.allScalarFields()
        .creator(UserFetcher.$.phone().nickname())
        .editor(UserFetcher.$.phone().nickname());
    default Page<${entityType.typeName}> findPage(QueryRequest<${entityType.typeName}Spec> queryRequest, Fetcher<${entityType.typeName}> fetcher) {
        ${entityType.typeName}Spec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return pager(pageable).execute(sql().createQuery(${uncapitalizeTypeName}Table)
                .where(query)
                .orderBy(SpringOrders.toOrders(${uncapitalizeTypeName}Table, pageable.getSort()))
                .select(${uncapitalizeTypeName}Table.fetch(fetcher)));
    }
}
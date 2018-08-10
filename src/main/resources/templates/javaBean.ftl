<#-----author : GuoJun------->
<#-----date : 2018-01-10----->
package ${baseConfig.basePackage}.${baseConfig.beanPackage};

<#if entity.importSet?? && (entity.importSet?size > 0) >
<#list entity.importSet as import>
import ${import};
</#list>
</#if>

/**
 *
 * @author  ${baseConfig.author}
 * @since   ${baseConfig.version}
 * Created by GuoJun on ${baseConfig.date}
 */
public class ${(entity.className)!} {

    <#if entity.fieldList?? && (entity.fieldList?size > 0) >
    <#list entity.fieldList as field>
    /**
     * This remarks: ${field.remarks}
     * This field corresponds to the database column ${(entity.tableName)!}.${(field.columnName)!}
     */
    private ${(field.type)!} ${(field.name)!};

    </#list>
    </#if>
    <#if entity.methodList?? && (entity.methodList?size > 0) >
    <#list entity.methodList as method>
    public ${(method.returnType)!} ${(method.name)!} (${(method.parameter)!}) {
        ${(method.methodBody)!}
    }

    </#list>
    </#if>
}
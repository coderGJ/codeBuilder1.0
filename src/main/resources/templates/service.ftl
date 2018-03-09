<#-----author : GuoJun------->
<#-----date : 2018-03-07----->
package ${baseConfig.basePackage}.${baseConfig.servicePackage};

import ${baseConfig.basePackage}.${baseConfig.beanPackage}.${entity.model};

/**
 *
 * @author  ${baseConfig.author}
 * @since   ${baseConfig.version}
 * @date    ${baseConfig.date}
 */
public interface ${(entity.className)!} extends BaseService<${(entity.model)!}, ${(entity.modelType)!}> {

}
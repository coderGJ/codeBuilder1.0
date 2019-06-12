<#-----author : GuoJun------->
<#-----date : 2018-03-07----->
package ${baseConfig.basePackage}.${baseConfig.servicePackage};

import ${baseConfig.basePackage}.${baseConfig.beanPackage}.${entity.model};

/**
 *
 * @author  ${baseConfig.author}
 * @since   ${baseConfig.version}
 * Created by GuoJun on ${baseConfig.date}
 */
public interface ${(entity.className)!} extends AbstractService<${(entity.model)!}, ${(entity.modelType)!}> {

}
<#-----author : GuoJun------->
<#-----date : 2018-03-07----->
package ${baseConfig.basePackage}.${baseConfig.servicePackage}.impl;

import ${baseConfig.basePackage}.${baseConfig.beanPackage}.${entity.model};
import ${baseConfig.basePackage}.${baseConfig.servicePackage}.${(entity.className)!};
import org.springframework.stereotype.Service;

/**
*
* @author  ${baseConfig.author}
* @since   ${baseConfig.version}
* Created by GuoJun on ${baseConfig.date}
*/
@Service
public class ${(entity.className)!}Impl extends AbstractServiceImpl<${(entity.model)!}, ${(entity.modelType)!}> implements ${(entity.className)!} {
    @Override
    public String getNamespace() {
        return ${(entity.model)!}.class.getName();
    }
}
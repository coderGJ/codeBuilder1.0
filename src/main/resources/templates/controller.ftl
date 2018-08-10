<#-----author : GuoJun------->
<#-----date : 2018-03-08----->
package ${baseConfig.basePackage}.${baseConfig.controllerPackage};

<#if entity.importList?? && (entity.importList?size > 0) >
<#list entity.importList as import>
<#if import != "">
import ${import};
<#else>

</#if>
</#list>
</#if>

import ${baseConfig.basePackage}.bean.Parameters;
import ${baseConfig.basePackage}.bean.ResponseData;
import ${baseConfig.basePackage}.${baseConfig.beanPackage}.${entity.model};
import ${baseConfig.basePackage}.${baseConfig.servicePackage}.BaseService.MethodName;
import ${baseConfig.basePackage}.${baseConfig.servicePackage}.${(entity.service)!};

/**
 *
 * @author  ${baseConfig.author}
 * @since   ${baseConfig.version}
 * Created by GuoJun on ${baseConfig.date}
 */
@Controller
@RequestMapping("/${(entity.model?uncap_first)}")
public class ${(entity.className)!} extends BaseController {

    @Autowired
    private ${(entity.service)!} ${(entity.service?uncap_first)!};

    @RequestMapping(value = "index.html")
    public String index(ModelMap model,@ModelAttribute("parameters") Parameters parameters) {
        model.put("pager", ${(entity.service?uncap_first)!}.page(parameters));
        return "${(entity.model?uncap_first)!}/index";
    }

    @RequestMapping(value = "add.html")
    public String add() {
        return "${(entity.model?uncap_first)!}/add";
    }

    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(ModelMap model, @ModelAttribute("entity") ${entity.model} entity) {
        entity = ${(entity.service?uncap_first)!}.save(entity);
        if (null == entity.getId()) {
            return new ResponseData(false, "保存数据时出错");
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "detail.html")
    public String detail(${(entity.idType)!} id, ModelMap model) {
        Parameters query = new Parameters();
        query.put("id", id);

        model.put("entity", ${(entity.service?uncap_first)!}.findOne(query));
        return "${(entity.model?uncap_first)!}/detail";
    }

    @RequestMapping(value = "edit.html")
    public String edit(${(entity.idType)!} id, ModelMap model) {
        Parameters query = new Parameters();
        query.put("id", id);

        model.put("entity", ${(entity.service?uncap_first)!}.findOne(query));
        return "${(entity.model?uncap_first)!}/edit";
    }

    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(@ModelAttribute("entity") ${entity.model} entity) {
        int result = ${(entity.service?uncap_first)!}.update(entity);
        if (result < 0) {
            return new ResponseData(false, "保存数据时出错");
        }
        return ResponseData.SUCCESS_NO_DATA;
    }

    @RequestMapping(value = "delete.json")
    @ResponseBody
    public ResponseData delete(${(entity.idType)!} id) {
        if (id == null) {
            return new ResponseData(false, "请选择一条记录");
        }
        int result = ${(entity.service?uncap_first)!}.delete(id);
        if (result < 0) {
            return new ResponseData(false, "删除数据时出错");
        }
        return ResponseData.SUCCESS_NO_DATA;
    }
}
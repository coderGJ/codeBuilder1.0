package com.guojun.app;

import com.guojun.base.GeneratorManager;
import com.guojun.dom.java.Field;
import com.guojun.dom.java.JavaBean;
import com.guojun.util.FreeMarkerUtil;
import com.guojun.util.JavaBeansUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class UtilTest {

    @Test
    public void testetCamelCaseString() {
        System.out.println(JavaBeansUtil.getCamelCaseString("BASE_cOnfig", true));
    }
    
    @Test
    public void testgetValidPropertyName() {
        System.out.println(JavaBeansUtil.getValidPropertyName("Baeconfig"));
    }
    
    @Test
    public void testManager() {
        GeneratorManager manager = new GeneratorManager();
        try {
            manager.getConditionBeanList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testFreeMarkerUtil() {
        try {
            FreeMarkerUtil.startTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGeneratorJavaBeanFile() {
        try {
            //String path = "";
            //String templateFtl = FreeMarkerUtil.TEMPLATE_JAVABEAN;
            JavaBean javaBean = new JavaBean();
            Set<String> importSet = new HashSet<>();
            importSet.add("java.util.Date");

            Field field0 = new Field();
            field0.setName("id");
            field0.setType("Integer");
            field0.setRemarks("id");
            field0.setColumnName("id");

            Field field1 = new Field();
            field1.setName("name");
            field1.setType("String");
            field1.setRemarks("用户姓名");
            field1.setColumnName("name");

            List<Field> list = new ArrayList<>();
            list.add(field0);
            list.add(field1);
            javaBean.setFieldList(list);

            javaBean.setImportSet(importSet);
            javaBean.setClassName("SysUser");
            javaBean.setTableName("sys_user");

            FreeMarkerUtil.generatorJavaBeanFile(javaBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

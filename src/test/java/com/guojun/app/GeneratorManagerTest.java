package com.guojun.app;

import com.guojun.base.GeneratorManager;
import com.guojun.dom.java.Controller;
import com.guojun.dom.java.JavaBean;
import com.guojun.dom.java.Mapper;
import com.guojun.dom.java.Service;
import com.guojun.util.FreeMarkerUtil;
import org.junit.Test;

import java.util.List;

public class GeneratorManagerTest {
    @Test
    public void testGetConditionalBeanList(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<JavaBean> list =  gm.getConditionBeanList();
            for (JavaBean bean : list) {
                FreeMarkerUtil.consoleJavaBean(bean);
                FreeMarkerUtil.generatorJavaBeanFile(bean);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConditionalMapperList(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Mapper> list =  gm.getConditionMapperList();
            for (Mapper mapper : list) {
                FreeMarkerUtil.consoleMapper(mapper);
                FreeMarkerUtil.generatorMapperXmlFile(mapper);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConditionalServiceList(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Service> list =  gm.getConditionServiceList();
            for (Service service : list) {
                FreeMarkerUtil.consoleService(service);
                FreeMarkerUtil.consoleServiceImpl(service);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConditionalServiceImplList(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Service> list =  gm.getConditionServiceList();
            for (Service service : list) {
                FreeMarkerUtil.consoleServiceImpl(service);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetConditionalControllerList(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Controller> list =  gm.getConditionControllerList();
            for (Controller controller : list) {
                FreeMarkerUtil.consoleController(controller);
                FreeMarkerUtil.generatorControllerFile(controller);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGeneratorServiceFile(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Service> list =  gm.getConditionServiceList();
            for (Service service : list) {
                FreeMarkerUtil.generatorServiceFile(service);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGeneratorServiceImplFile(){
        GeneratorManager gm = new GeneratorManager();
        try {
            List<Service> list =  gm.getConditionServiceList();
            for (Service service : list) {
                FreeMarkerUtil.generatorServiceImplFile(service);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

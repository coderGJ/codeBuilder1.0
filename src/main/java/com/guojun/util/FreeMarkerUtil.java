package com.guojun.util;

import com.guojun.config.BaseConfiguration;
import com.guojun.config.Context;
import com.guojun.dom.java.Controller;
import com.guojun.dom.java.JavaBean;
import com.guojun.dom.java.Mapper;
import com.guojun.dom.java.Service;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker模板引擎生成模板
 * 
 * @author      GuoJun
 * @since       1.0
 */
public class FreeMarkerUtil {

    private static Configuration configuration;
    private static Template template;
    private static Writer writer;

    public static final String TEMPLATE_BASE = "/templates";
    public static final String TEMPLATE_JAVABEAN = "javaBean.ftl";
    public static final String TEMPLATE_MAPPER = "mapper.ftl";
    public static final String TEMPLATE_SERVICE = "service.ftl";
    public static final String TEMPLATE_CONTROLLER = "controller.ftl";
    public static final String TEMPLATE_SERVICE_IMPL = "serviceImpl.ftl";
    /**
     * 利用模板在控制台打印helloWorld信息
     *
     * @throws Exception
     */
    public static void startTest() throws Exception {
        // 创建Freemarker配置实例
        configuration = getConfiguration();

        // 创建数据模型
        Map<String, String> root = new HashMap<String, String>();
        root.put("message", "Hello,World");

        // 加载模板文件
        template = configuration.getTemplate("test.ftl");

        // 显示生成的数据，这里打印在控制台
        writer = new OutputStreamWriter(System.out);
        template.process(root, writer);
        writer.flush();
        writer.close();
    }

    /**
     * 控制台打印JavaBean
     * @param javaBean
     * @throws Exception
     */
    public static void consoleJavaBean(JavaBean javaBean) throws Exception {
        generatorConsole(TEMPLATE_JAVABEAN, javaBean);
    }

    /**
     * 控制台打印Mapper
     * @param mapper
     * @throws Exception
     */
    public static void consoleMapper(Mapper mapper) throws Exception {
        generatorConsole(TEMPLATE_MAPPER, mapper);
    }

    /**
     * 控制台打印Service
     * @param service
     * @throws Exception
     */
    public static void consoleService(Service service) throws Exception {
        generatorConsole(TEMPLATE_SERVICE, service);
    }

    /**
     * 控制台打印ServiceImpl
     * @param service
     * @throws Exception
     */
    public static void consoleServiceImpl(Service service) throws Exception {
        generatorConsole(TEMPLATE_SERVICE_IMPL, service);
    }

    /**
     * 控制台打印Mapper
     * @param controller
     * @throws Exception
     */
    public static void consoleController(Controller controller) throws Exception {
        generatorConsole(TEMPLATE_CONTROLLER, controller);
    }
    /**
     *
     * @param javaBean
     * @throws Exception
     */
    public static void generatorJavaBeanFile(JavaBean javaBean) throws Exception {
        Context context = Context.getContext();
        BaseConfiguration bc = context.getBaseConfiguration();
        StringBuffer sb = new StringBuffer(bc.getCompletePath());
        sb.append(bc.getBeanPackage()).append("/").append(javaBean.getFileName());
        generatorFile(TEMPLATE_JAVABEAN, sb.toString(), javaBean);
    }
    
    
    /**
     *
     * @param mapper
     * @throws Exception
     */
    public static void generatorMapperXmlFile(Mapper mapper) throws Exception {
        Context context = Context.getContext();
        BaseConfiguration bc = context.getBaseConfiguration();
        StringBuffer sb = new StringBuffer(bc.getCompletePath());
        sb.append(bc.getMapperPackage()).append("/").append(mapper.getFileName());
        generatorFile(TEMPLATE_MAPPER, sb.toString(), mapper);
    }

    /**
     *
     * @param service
     * @throws Exception
     */
    public static void generatorServiceFile(Service service) throws Exception {
        Context context = Context.getContext();
        BaseConfiguration bc = context.getBaseConfiguration();
        StringBuffer sb = new StringBuffer(bc.getCompletePath());
        sb.append(bc.getServicePackage()).append("/").append(service.getClassName()).append(".java");
        generatorFile(TEMPLATE_SERVICE, sb.toString(), service);
    }

    /**
     *
     * @param service
     * @throws Exception
     */
    public static void generatorServiceImplFile(Service service) throws Exception {
        Context context = Context.getContext();
        BaseConfiguration bc = context.getBaseConfiguration();
        StringBuffer sb = new StringBuffer(bc.getCompletePath());
        sb.append(bc.getServicePackage()).append("/impl/").append(service.getClassName()).append("Impl").append(".java");
        generatorFile(TEMPLATE_SERVICE_IMPL, sb.toString(), service);
    }

    /**
     *
     * @param controller
     * @throws Exception
     */
    public static void generatorControllerFile(Controller controller) throws Exception {
        Context context = Context.getContext();
        BaseConfiguration bc = context.getBaseConfiguration();
        StringBuffer sb = new StringBuffer(bc.getCompletePath());
        sb.append(bc.getControllerPackage()).append("/").append(controller.getFileName());
        generatorFile(TEMPLATE_CONTROLLER, sb.toString(), controller);
    }

    /** 
     * 获取freemarker的配置 freemarker本身支持classpath
     * @return 返回Configuration对象 
     */  
    private static Configuration getConfiguration() {
        if (null == configuration) {  
            configuration = new Configuration();
            configuration.setClassForTemplateLoading(FreeMarkerUtil.class, TEMPLATE_BASE);
            // 设置国家及其编码
            configuration.setEncoding(Locale.getDefault(), "UTF-8");
              
            // 设置对象的包装器  
            configuration.setObjectWrapper(new DefaultObjectWrapper());
            // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错 
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }
        return configuration;
    }
    
    private static String getFilePath(BaseConfiguration baseConfig) {
        StringBuilder sb = new StringBuilder(baseConfig.getBasePackage());
        if (!sb.toString().endsWith(".")) {
            sb.append(".");
        }
        sb.append(baseConfig.getBeanPackage());
        return "";
    }

    /**
     * 在控制台打印根据模板生成的信息
     * @param templateFtl
     * @param object
     * @throws Exception
     */
    private static void generatorConsole(String templateFtl, Object object) throws Exception {
        // 创建Freemarker配置实例
        configuration = getConfiguration();

        Context context = Context.getContext();
        BaseConfiguration bg = context.getBaseConfiguration();
        // 创建数据模型
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("baseConfig", bg);
        data.put("entity", object);
        // 加载模板文件
        template = configuration.getTemplate(templateFtl);

        // 显示生成的数据
        writer = new OutputStreamWriter(System.out);
        template.process(data, writer);
        writer.flush();
        //writer.close();
    }

    /**
     * 在控制台打印根据模板生成的信息
     * @param templateFtl
     * @param object
     * @throws Exception
     */
    private static void generatorFile(String templateFtl, String outputFile, Object object) throws Exception {
        // 创建Freemarker配置实例
        configuration = getConfiguration();

        Context context = Context.getContext();
        BaseConfiguration bg = context.getBaseConfiguration();
        // 创建数据模型
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("baseConfig", bg);
        data.put("entity", object);
        // 加载模板文件
        template = configuration.getTemplate(templateFtl);

        File filePath = new File(outputFile);
        if (!filePath.getParentFile().exists()) {
            boolean result = filePath.getParentFile().mkdirs();
            if (!result) {
                throw new IOException("创建文件夹失败");
            }
        }

        if (filePath.exists()) {
            throw new Exception(outputFile + " 文件已存在");
        }
        boolean result = filePath.createNewFile();
        if (!result) {
            throw new IOException("创建文件失败");
        }
        // 显示生成的数据
        writer = new FileWriter(filePath);
        template.process(data, writer);
        writer.flush();
        writer.close();
    }
}
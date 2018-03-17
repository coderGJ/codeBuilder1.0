package com.guojun.base;

import com.guojun.config.Constants;
import com.guojun.config.Context;
import com.guojun.config.TableConfiguration;
import com.guojun.db.ConnectionFactory;
import com.guojun.dom.Column;
import com.guojun.dom.Table;
import com.guojun.dom.java.*;
import com.guojun.util.FreeMarkerUtil;
import com.guojun.util.JavaBeansUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class GeneratorManager {

    private static Logger logger = LoggerFactory.getLogger(GeneratorManager.class);

    private static final String COMMENT = "COMMENT='";

    private static final String DATE_IMPORT = "java.util.Date";

    private static final String VOID_TYPE = "void";

    private static final String RETURN = "return";

    private static final String TAILED = ";";

    private static final String THIS = "this.";

    private static final String SHOW_TABLE = "SHOW CREATE TABLE ";

    private static final String SUFFIX_MAPPER = "Mapper";

    private static final String SUFFIX_SERVICE = "Service";

    private static final String SUFFIX_IMPL = "Impl";

    private static final String SUFFIX_CONTROLLER  = "Controller";

    private static final String SUFFIX_JAVA = ".java";

    private static final String SUFFIX_XML = ".xml";

    private static Pattern PATTERN_INCLUDE = null;

    private static Pattern PATTERN_EXCLUDE = null;

    private static List<Table> tableList;

    private static List<String> controllerImports;

    static {
        Context context = Context.getContext();
        TableConfiguration tableConfig = context.getTableConfiguration();
        String include = tableConfig.getInclude();
        String exclude = tableConfig.getExclude();
        PATTERN_INCLUDE = Pattern.compile(include.replace(",", "|"));
        PATTERN_EXCLUDE = Pattern.compile(exclude.replace(",", "|"));
        initImports();
    }

    private static void initImports() {
        controllerImports = new ArrayList<String>();
        controllerImports.add("java.util.HashMap");
        controllerImports.add("java.util.List");
        controllerImports.add("java.util.Map");
        controllerImports.add("");

        controllerImports.add("org.springframework.beans.factory.annotation.Autowired");
        controllerImports.add("org.springframework.stereotype.Controller");
        controllerImports.add("org.springframework.ui.ModelMap");
        controllerImports.add("org.springframework.web.bind.annotation.ModelAttribute");
        controllerImports.add("org.springframework.web.bind.annotation.RequestMapping");
        controllerImports.add("org.springframework.web.bind.annotation.RequestMethod");
        controllerImports.add("org.springframework.web.bind.annotation.ResponseBody");
    }
    /**
     * 获得满足条件的beanList
     * @return
     * @throws Exception
     */
    public List<JavaBean> getConditionBeanList() throws Exception {
        List<JavaBean> beanList = new ArrayList<JavaBean>();
        if (tableList == null) {
            tableList = getConditionTableList();
        }
        String tableName = "";
        if (!tableList.isEmpty()) {
            for (Table table : tableList) {
                tableName = table.getTableName();
                String className = JavaBeansUtil.getCamelCaseString(tableName,true);
                JavaBean javaBean = new JavaBean();
                javaBean.setFileName(className.concat(SUFFIX_JAVA));
                javaBean.setTableName(tableName);
                javaBean.setClassName(className);
                Set<String> importSet = new HashSet<String>();
                List<Field> fieldList = new ArrayList<Field>();
                List<Method> methodList = new ArrayList<Method>();
                if (table.getPrimaryColumn() != null) {
                    Field field = column2Field(table.getPrimaryColumn());
                    fieldList.add(field);
                    methodList.add(getGetterMethod(field));
                    methodList.add(getSetterMethod(field));
                }
                for (Column column : table.getColumnList()) {
                    Field field = column2Field(column);
                    fieldList.add(field);
                    if (field.getType().equals("Date")) {
                        importSet.add(DATE_IMPORT);
                    }
                    methodList.add(getGetterMethod(field));
                    methodList.add(getSetterMethod(field));
                }
                javaBean.setImportSet(importSet);
                javaBean.setFieldList(fieldList);
                javaBean.setMethodList(methodList);
                beanList.add(javaBean);
            }
        }
        return beanList;
    }

    /**
     * 获得满足条件的mapperList
     * @return
     * @throws Exception
     */
    public List<Mapper> getConditionMapperList() throws Exception {
        List<Mapper> mapperList = new ArrayList<Mapper>();
        if (tableList == null) {
            tableList = getConditionTableList();
        }
        String tableName = "";
        if (!tableList.isEmpty()) {
            for (Table table : tableList) {
                tableName = table.getTableName();
                String className = JavaBeansUtil.getCamelCaseString(tableName,true);
                Mapper mapper = new Mapper();
                mapper.setFileName(className.concat(SUFFIX_MAPPER).concat(SUFFIX_XML));
                mapper.setTableName(tableName);
                mapper.setClassName(className);
                mapper.setPrimaryColumn(table.getPrimaryColumn());
                mapper.setColumnList(table.getColumnList());
                mapperList.add(mapper);
            }
        }
        return mapperList;
    }

    /**
     * 获得满足条件的serviceList
     * @return
     * @throws Exception
     */
    public List<Service> getConditionServiceList() throws Exception {
        List<Service> serviceList = new ArrayList<Service>();
        if (tableList == null) {
            tableList = getConditionTableList();
        }
        String tableName = "";
        if (!tableList.isEmpty()) {
            for (Table table : tableList) {
                tableName = table.getTableName();
                String className = JavaBeansUtil.getCamelCaseString(tableName,true);
                Service service = new Service();
                service.setClassName(className.concat(SUFFIX_SERVICE));
                service.setModel(className);
                service.setModelType(JavaBeansUtil.jdbcType2JavaType(table.getPrimaryColumn().getColumnType()));
                serviceList.add(service);
            }
        }
        return serviceList;
    }

    /**
     * 获得满足条件的controllerList
     * @return
     * @throws Exception
     */
    public List<Controller> getConditionControllerList() throws Exception {
        List<Controller> controllerList = new ArrayList<Controller>();
        if (tableList == null) {
            tableList = getConditionTableList();
        }
        String tableName = "";
        if (!tableList.isEmpty()) {
            String basePackage = Context.getContext().getBaseConfiguration().getBasePackage();
            String modelPath = basePackage + ".";
            for (Table table : tableList) {
                tableName = table.getTableName();
                String className = JavaBeansUtil.getCamelCaseString(tableName,true);
                Controller controller = new Controller();
                controller.setClassName(className.concat(SUFFIX_CONTROLLER));
                controller.setFileName(className.concat(SUFFIX_CONTROLLER).concat(SUFFIX_JAVA));
                controller.setService(className.concat(SUFFIX_SERVICE));
                controller.setImportList(controllerImports);
                controller.setModel(className);
                controller.setIdType(JavaBeansUtil.jdbcType2JavaType(table.getPrimaryColumn().getColumnType()));
                controllerList.add(controller);
            }
        }
        return controllerList;
    }

    public void generatorFiles() {
        threadGeneratorBean();
        threadGeneratorMapper();
        threadGeneratorService();
        threadGeneratorController();
    }
    /**
     * 获得满足条件的tableList
     * @return
     * @throws Exception
     */
    private synchronized List<Table> getConditionTableList() throws Exception {
        List<Table> tableList = new ArrayList<Table>();

        ConnectionFactory cf = ConnectionFactory.getInstance();
        Connection connection = cf.getConnection();
        DatabaseMetaData dbMetaData = connection.getMetaData();
        databaseInfo(dbMetaData);

        String[] typeList = new String[] { "TABLE" };
        //库名(database)
        String catalog = connection.getCatalog();
        //查询所有表
        ResultSet rs = dbMetaData.getTables(catalog, "%", "%", typeList);
        String name = "";
        String type = "";
        String tableRemarks = "";
        String pk = "";
        ResultSet colRet = null;

        /*
            TABLE_CAT String => 表类别（可为 null）
            TABLE_SCHEM String => 表模式（可为 null）
            TABLE_NAME String => 表名称
            TABLE_TYPE String => 表类型。
            REMARKS String => 表的解释性注释
            TYPE_CAT String => 类型的类别（可为 null）
            TYPE_SCHEM String => 类型模式（可为 null）
            TYPE_NAME String => 类型名称（可为 null）
            SELF_REFERENCING_COL_NAME String => 有类型表的指定 "identifier" 列的名称（可为 null）
            REF_GENERATION String
         */
        while (rs.next()) {
            //printResultSet(rs);

            name = rs.getString("TABLE_NAME");
            type = rs.getString("TABLE_TYPE");
            tableRemarks = rs.getString("REMARKS");

            if (type.equalsIgnoreCase("TABLE") && name.indexOf("$") == -1) {
                if (filterInclude(name) && !filterExclude(name)) {
                    ResultSet pkRet = dbMetaData.getPrimaryKeys(connection.getCatalog(), null, name);

                    while ( pkRet.next()) {
                        pk = (String)pkRet.getObject("COLUMN_NAME");
                    }

                    Table table = new Table();
                    table.setTableName(name);
                    table.setTableType(type);
                    if (StringUtils.isEmpty(tableRemarks)) {
                        tableRemarks = getTableComment(name);
                    }
                    table.setRemarks(tableRemarks);

                    List<Column> columnList = new ArrayList<Column>();
                    colRet = dbMetaData.getColumns(connection.getCatalog(), "%", name, "%");
                    while (colRet.next()) {
                        Column column = createColumn(colRet, pk);
                        if (column.isPrimaryKey()) {
                            table.setPrimaryColumn(column);
                        } else {
                            columnList.add(column);
                        }
                        logger.debug(column.toString());
                    }
                    table.setColumnList(columnList);

                    tableList.add(table);
                }
            }
        }
        rs.close();
        return tableList;
    }

    private String getTableComment(String tableName) throws SQLException {
        ConnectionFactory cf = ConnectionFactory.getInstance();
        Connection connection = cf.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(SHOW_TABLE .concat(tableName));
        String comment = "";
        if (rs != null && rs.next()) {
            String create = rs.getString("Create Table");
            int index = create.indexOf(COMMENT);
            if (index > 0) {
                comment = create.substring(index + COMMENT.length());
                comment = comment.substring(0, comment.length() - 1);
                try {
                    comment = new String(comment.getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return comment;
    }

    private List<Table> filterTable(List<Table> allTable) {
        if (allTable != null && !allTable.isEmpty()) {
            Context context = Context.getContext();
            TableConfiguration tableConfig = context.getTableConfiguration();
            String include = tableConfig.getInclude();
            String exclude = tableConfig.getExclude();
            Pattern p = null;
            boolean eligible = true;
            if (StringUtils.isNotEmpty(include)) {
                p = Pattern.compile(include);
            } else if (StringUtils.isNotEmpty(exclude)) {
                p = Pattern.compile(include);
                eligible = false;
            }
            if (p != null) {
                for (Table table : allTable) {
                    if (eligible) {
                        if (!p.matcher(table.getTableName()).matches()) {
                            allTable.remove(table);
                        }
                    } else {
                        if (p.matcher(table.getTableName()).matches()) {
                            allTable.remove(table);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获得包含的表
     * @param tableName
     * @return
     */
    private boolean filterInclude(String tableName) {
        return PATTERN_INCLUDE.matcher(tableName).matches();
    }

    /**
     * 去除不包含的表
     * @param tableName
     * @return
     */
    private boolean filterExclude(String tableName) {
        return PATTERN_EXCLUDE.matcher(tableName).matches();
    }

    private void databaseInfo(DatabaseMetaData metaData) {
        try {
            logger.debug("数据库产品名：" + metaData.getDatabaseProductName());
            logger.debug("数据库版本号：" + metaData.getDatabaseProductVersion());
            logger.debug("数据库驱动名：" + metaData.getDriverName());
            logger.debug("数据库驱动版本号：" + metaData.getDriverVersion());
            logger.debug("当前连接的URL:" + metaData.getURL());
            logger.debug("当前连接的用户名:" + metaData.getUserName());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get database information throws error!");
        }
    }

    private Column createColumn(ResultSet columnResult, String primaryKey) throws SQLException {
        Column column = new Column();
        String columnName = columnResult.getString("COLUMN_NAME");
        String typeName = columnResult.getString("TYPE_NAME");
        int nullable = columnResult.getInt("NULLABLE");

        column.setCamelCaseName(JavaBeansUtil.getCamelCaseString(columnName, false));
        column.setColumnName(columnName);
        column.setColumnType(typeName);
        if ("INT".equals(typeName)) {
            typeName = "INTEGER";
        } else if ("DATETIME".equals(typeName)) {
            typeName = "TIMESTAMP";
        }
        column.setJdbcType(typeName);
        column.setColumnSize(columnResult.getInt("COLUMN_SIZE"));
        column.setDigits(columnResult.getInt("DECIMAL_DIGITS"));
        column.setRemarks(columnResult.getString("REMARKS"));
        if (nullable == Constants.DISALLOW_NULL) {
            column.setNullable(true);
        } else {
            column.setNullable(false);
        }
        if (columnName.equals(primaryKey)) {
            column.setPrimaryKey(true);
        } else {
            column.setPrimaryKey(false);
        }
        return column;
    }

    private Field column2Field(Column column) {
        Field field = new Field();
        if (column != null) {
            String columnName = column.getColumnName();
            field.setName(column.getCamelCaseName());
            field.setColumnName(columnName);
            field.setType(JavaBeansUtil.jdbcType2JavaType(column.getColumnType()));
            field.setRemarks(column.getRemarks());
        }
        return field;
    }

    private static Method getGetterMethod(Field field) {
        Method method = new Method();
        if (field != null) {
            String methodName = JavaBeansUtil.getGetterMethodName(field.getName(), field.getType());
            method.setName(methodName);
            method.setReturnType(field.getType());
            method.setParameter("");
            method.setMethodBody(RETURN + " " + field.getName() + TAILED);
            logger.debug(method.toString());
        }
        return method;
    }

    private static Method getSetterMethod(Field field) {
        Method method = new Method();
        if (field != null) {
            String name = field.getName();
            String methodName = JavaBeansUtil.getSetterMethodName(name);
            method.setName(methodName);
            method.setReturnType(VOID_TYPE);
            method.setParameter(field.getType() + " " + name);
            method.setMethodBody(THIS + name + " = " + name + TAILED);
            logger.debug(method.toString());
        }
        return method;
    }

    /**
     * 打印ResultSet信息
     * @param rs
     * @throws SQLException
     */
    private void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();
        for(int i = 1; i <= columns; i++) {
            System.out.print("name:" + metaData.getColumnName(i) + "|" + rs.getString(i) + "\t");
        }
        System.out.println();
    }

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    private static String fullyQualifiedName(String basePackage, String className){
        return basePackage + "." + className;
    }

    private void threadGeneratorBean() {
        (new Thread() {
            @Override
            public void run() {
                List<JavaBean> list = new ArrayList<JavaBean>();
                try {
                    list = getConditionBeanList();

                } catch (Exception e) {
                    logger.error("getConditionBeanList error");
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                for (JavaBean bean : list) {
                    try {
                        FreeMarkerUtil.generatorJavaBeanFile(bean);
                    } catch (Exception e) {
                        logger.error("generatorJavaBeanFile error");
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).run();
    }

    private void threadGeneratorMapper() {
        (new Thread() {
            @Override
            public void run() {
                List<Mapper> list = new ArrayList<Mapper>();
                try {
                    list = getConditionMapperList();
                } catch (Exception e) {
                    logger.error("getConditionBeanList error");
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                for (Mapper mapper : list) {
                    try {
                        FreeMarkerUtil.generatorMapperXmlFile(mapper);
                    } catch (Exception e) {
                        logger.error("generatorMapperXmlFile error");
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).run();
    }

    private void threadGeneratorService() {
        (new Thread() {
            @Override
            public void run() {
                try {
                    List<Service> list = getConditionServiceList();
                    for (Service service : list) {
                        FreeMarkerUtil.generatorServiceFile(service);
                        FreeMarkerUtil.generatorServiceImplFile(service);
                    }
                } catch (Exception e) {
                    logger.error("generatorServiceFile error");
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).run();
    }

    private void threadGeneratorController() {
        (new Thread() {
            @Override
            public void run() {
                try {
                    List<Controller> list = getConditionControllerList();
                    for (Controller controller : list) {
                        FreeMarkerUtil.generatorControllerFile(controller);
                    }
                } catch (Exception e) {
                    logger.error("generatorControllerFile error");
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).run();
    }
}

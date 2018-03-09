package com.guojun.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.guojun.base.AppProperties;

/**
 * 
 * @author    GuoJun
 * @since     1.0
 */
public class Context {
    
    private volatile static Context context;

    // date format
    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    
    private JDBCConfiguration jdbcConfiguration;

    private BaseConfiguration baseConfiguration;
    
    private TableConfiguration tableConfiguration;

    private Context() {}
    
    public static Context getContext() {
        if (context == null) {
            synchronized (Context.class) {
                if (context == null) {
                    init();
                }
            }
        }
        return context;
    }

    public JDBCConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JDBCConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public BaseConfiguration getBaseConfiguration() {
        return baseConfiguration;
    }

    public void setBaseConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public TableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }
    
    private static void init() {
        context = new Context();
        JDBCConfiguration jdbcConfiguration = new JDBCConfiguration();
        jdbcConfiguration.setDriverClass(AppProperties.getProperty("jdbc.driverClassName"));
        jdbcConfiguration.setConnectionURL(AppProperties.getProperty("jdbc.url"));
        jdbcConfiguration.setUser(AppProperties.getProperty("jdbc.user"));
        jdbcConfiguration.setPassword(AppProperties.getProperty("jdbc.password"));
        context.setJdbcConfiguration(jdbcConfiguration);
        
        BaseConfiguration bc = new BaseConfiguration();
        bc.setAuthor(AppProperties.getProperty("base.author", "unascribed"));
        //bc.setBasePackage(AppProperties.getProperty("base.basePackage", System.getProperty("user.dir")));
        bc.setBasePackage(AppProperties.getProperty("base.basePackage", "com." + bc.getAuthor()));
        bc.setVersion(AppProperties.getProperty("base.version", "1.0"));
        bc.setBeanPackage(AppProperties.getProperty("base.beanPackage", "model"));
        bc.setServicePackage(AppProperties.getProperty("base.servicePackage", "service"));
        bc.setControllerPackage(AppProperties.getProperty("base.controllerPackage", "controller"));
        bc.setMapperPackage(AppProperties.getProperty("base.mapperPackage", "mapper"));
        bc.setPagePackage(AppProperties.getProperty("base.pages", "pages"));
        bc.setOutputPath(AppProperties.getProperty("base.outputPath", "../"));
        String completePath = getCompletePath(bc);
        bc.setCompletePath(completePath);
        SimpleDateFormat sdf = new SimpleDateFormat(AppProperties.getProperty("base.dateFormat",
            DEFAULT_DATE_FORMAT));
        bc.setDate(sdf.format(new Date()));
        context.setBaseConfiguration(bc);
        
        TableConfiguration tableConfiguration = new TableConfiguration();
        tableConfiguration.setExclude(AppProperties.getProperty("table.exclude"));
        tableConfiguration.setInclude(AppProperties.getProperty("table.include"));
        context.setTableConfiguration(tableConfiguration);
    }

    private static String getCompletePath(BaseConfiguration bc) {
        StringBuffer sb = new StringBuffer(System.getProperty("user.dir"));
        sb.append("/").append(bc.getOutputPath()).append(bc.getBasePackage().replace(".", "/"));
        if (!sb.toString().endsWith("/")) {
            sb.append("/");
        }
        return sb.toString();
    }
}

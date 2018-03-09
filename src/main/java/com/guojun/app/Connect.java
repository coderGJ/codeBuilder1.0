package com.guojun.app;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guojun.base.AppProperties;

/**
 *
 * @author      GuoJun
 * @since       20170612
 * 
 */
public class Connect {

    private static Logger logger = LoggerFactory.getLogger(Connect.class);

    public static void main(String[] args) {
        try {
            //加载MySql的驱动类
            String driver = AppProperties.getProperty("jdbc.driverClassName");
            Class.forName(driver);
        } catch(ClassNotFoundException e) {
            logger.debug("找不到驱动程序类，加载驱动失败！");
            e.printStackTrace();
        }
        
        String url = AppProperties.getProperty("jdbc.url");
        String userName = AppProperties.getProperty("jdbc.user");
        String password = AppProperties.getProperty("jdbc.password");
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData dbmd = connection.getMetaData();
            List<String> tableList = new ArrayList<String>();
            String[] typeList = new String[] { "TABLE" };
            ResultSet rs = dbmd.getTables(connection.getCatalog(), "%", "%", typeList);
            String name = "";
            String type = "";
            String tableRemarks = "";
            
            String columnName = "";
            String columnType = "";
            String remarks = "";
            ResultSet colRet = null;
            while (rs.next()) {
                name = rs.getString("TABLE_NAME");
                type = rs.getString("TABLE_TYPE");
                tableRemarks = rs.getString("REMARKS");
                /*for (int i = 1 ; i <= 10; i++ ) {
                    System.out.print(rs.getString(i) + "\t");
                }
                if (type.equalsIgnoreCase("TABLE") && name.indexOf("$") == -1) {
                    System.out.println("tableRemarks:" + tableRemarks);
                    tableList.add(name);
                }*/
                if (type.equalsIgnoreCase("TABLE") && name.indexOf("$") == -1) {
                    colRet = dbmd.getColumns(connection.getCatalog(),"%",name,"%");
                    logger.debug("-----------------" + name + "\ttableRemarks:"+ tableRemarks + "---------------");
                    while(colRet.next()) {
                        columnName = colRet.getString("COLUMN_NAME");
                        columnType = colRet.getString("TYPE_NAME");
                        int datasize = colRet.getInt("COLUMN_SIZE");
                        int digits = colRet.getInt("DECIMAL_DIGITS");
                        int nullable = colRet.getInt("NULLABLE");
                        remarks = colRet.getString("REMARKS");
                        logger.debug("columnName:" + columnName + "\tcolumnType:" + columnType +" "+datasize+" "+digits+" "+ nullable + "\tremarks:" + remarks); 
                    }
                    tableList.add(name);
                }
            }

        } catch(SQLException se) {
            logger.debug("数据库连接失败！");
            logger.debug(se.getMessage());
        }
    }
}

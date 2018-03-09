package com.guojun.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      GuoJun
 * @since       20170724
 * 
 */
public class MysqlTest {

    Logger logger = LoggerFactory.getLogger(MysqlTest.class);
    
    public void testConnet() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/qmxx?user=root&password=root");
            // Do something with the Connection


            
        } catch (SQLException ex) {
            // handle any errors
            logger.error("SQLException: " + ex.getMessage());
            logger.error("SQLState: " + ex.getSQLState());
            logger.error("VendorError: " + ex.getErrorCode());
        }
        try {
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM sys_user";
            ResultSet resultSet = statement.executeQuery(sql);
            logger.debug(resultSet.getLong(1) + "----" + resultSet.getString(2) + "-----" + resultSet.getString(3));
            while (resultSet.next()) {
                logger.debug(resultSet.getLong(1) + "----" + resultSet.getString(2) + "-----" + resultSet.getString(3));
            }
        } catch (SQLException ex) {
            // handle any errors
            logger.error("SQLException: " + ex.getMessage());
            logger.error("SQLState: " + ex.getSQLState());
            logger.error("VendorError: " + ex.getErrorCode());
        }
    }
    public static void main (String[] args) {
        MysqlTest test = new MysqlTest();
        test.testConnet();
    }
}

package com.guojun.app;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author      GuoJun
 * @since       20170612
 * 
 */
public class TestTableInfo {
    
    private static String url = "jdbc:mysql://localhost:3306/guojun?characterEncoding=UTF-8&amp;useUnicode=true";
    private static String user = "root";
    private static String pwd = "root";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pwd);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static Statement stmt = null;
    private static List<String> tableNameList = new ArrayList<String>();

    public static Map<String,String> getCommentByTableName() throws Exception {
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0; i < tableNameList.size(); i++) {
            String table = (String) tableNameList.get(i);
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnName(2));
            if (rs != null && rs.next()) {
                String create = rs.getString(2);
                String comment = parse(create);
                map.put(table, comment);
            }
            rs.close();
        }
        return map;
    }
    


    public static void getAllTableName() throws Exception {
        Connection conn = getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tableNameList.add(tableName);
        }
        rs.close();
    }

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        try {
            comment = new String(comment.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public static void main(String[] args) throws Exception {
        getAllTableName();
        Map<String,String> tablesComment = getCommentByTableName();
        for(String name : tablesComment.keySet()) {
            System.out.println("Table Name: " + name + ", Comment: "+ tablesComment.get(name));
        }
    }


}

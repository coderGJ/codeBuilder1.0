package com.guojun.db;

import com.guojun.config.Context;
import com.guojun.config.JDBCConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author      GuoJun
 * @since       1.0
 * 
 */
public class ConnectionFactory {

    private ConnectionFactory() {
        super();
    }

    private static ConnectionFactory instance = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        JDBCConfiguration config = Context.getContext().getJdbcConfiguration();
        try {
            String driver = config.getDriverClass();
            Class.forName(driver);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException("Exception getting JDBC Driver", e); //$NON-NLS-1$
        }
        String url = config.getConnectionURL();
        String user = config.getUser();
        String password = config.getPassword();

        Connection conn = DriverManager.getConnection(url, user, password);

        if (conn == null) {
            throw new SQLException("Cannot connect to database (possibly bad driver/URL combination)"); //$NON-NLS-1$
        }

        return conn;
    }

}
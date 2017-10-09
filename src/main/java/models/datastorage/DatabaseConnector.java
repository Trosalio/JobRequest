package models.datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Project Name: MemoView
 */

public abstract class DatabaseConnector{

    protected String JDBC_DRIVER;
    protected String JDBC_URL;

    public Connection getDatabaseConnection(){
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected abstract String initJDBC_URL();
}

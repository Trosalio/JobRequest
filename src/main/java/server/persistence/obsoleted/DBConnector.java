package server.persistence.obsoleted;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public abstract class DBConnector {

    protected String JDBC_URL = setUpDriver();

    public Connection getDatabaseConnection(){
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public abstract String setUpDriver();

    protected void updateDatabase(String updateSQL) {
        try (Connection connection = getDatabaseConnection();
             PreparedStatement pStmt = connection.prepareStatement(updateSQL)) {
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void createTableIfNotExist();


}

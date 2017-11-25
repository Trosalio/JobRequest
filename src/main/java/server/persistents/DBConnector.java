package server.persistents;

import org.sqlite.SQLiteConfig;

import java.sql.*;

/**
 * Project Name: MemoView
 */

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

    protected abstract void createTableIfNotExist();

    public abstract ResultSet loadItemsFromDatabase();

    protected abstract void pullDataToEventList() throws SQLException;

    protected abstract void insertItemToDatabase();

    protected abstract void modifyItemInDatabase();

    public abstract void deleteItemInDatabase(int ID);

    protected void updateDatabase(String updateSQL) {
        try (Connection connection = getDatabaseConnection();
             PreparedStatement pStmt = connection.prepareStatement(updateSQL)) {
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract String setUpDriver();
}

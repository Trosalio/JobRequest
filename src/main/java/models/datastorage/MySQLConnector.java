package models.datastorage;

/**
 * Project Name: MemoView
 */

public class MySQLConnector extends  DatabaseConnector{

    public MySQLConnector(){
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        JDBC_URL = initJDBC_URL();
    }

    @Override
    protected String initJDBC_URL() {
        String jdbc_url = "jdbc:mysql://localhost:3306/?user=root";
        return jdbc_url;
    }
}

package models.persistents;

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
        return "jdbc:mysql://localhost:3306/?user=root";
    }
}

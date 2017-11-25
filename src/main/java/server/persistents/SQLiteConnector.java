package server.persistents;


import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project Name: MemoView
 */

public class SQLiteConnector extends DBConnector {

    @Override
    public String setUpDriver() {
        String jdbc_url = "jdbc:sqlite:";
        File dbFile = new File("database.db");
        if (!dbFile.exists()) try {
            boolean success = dbFile.createNewFile();
            if (success) System.out.println("Successfully create new DB file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbc_url = jdbc_url + dbFile;
        return jdbc_url;
    }

    @Override
    protected void createTableIfNotExist() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Advertise" +
                "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "refNumber TEXT NOT NULL," +
                "createDate TEXT NOT NULL," +
                "startDate TEXT NOT NULL," +
                "endDate TEXT NOT NULL," +
                "formID INTEGER NOT NULL," +
                "FOREIGN KEY(formID) REFERENCES Form(ID));";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS Form" +
                "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "detailName TEXT NOT NULL," +
                "requester TEXT NOT NULL," +
                "typeOfMedia TEXT NOT NULL," +
                "quantity TEXT NOT NULL," +
                "startDate TEXT NOT NULL," +
                "endDate TEXT NOT NULL," +
                "status TEXT NOT NULL);";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS StationStation" +
                "(code TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "PRIMARY KEY(code));";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS Stations_In_Form " +
                "(formID INTEGER NOT NULL," +
                "locationCode TEXT NOT NULL," +
                "FOREIGN KEY(formID) REFERENCES Form(ID)," +
                "FOREIGN KEY(locationCode) REFERENCES Station(code)," +
                "PRIMARY KEY(formID,locationCode));";
        updateDatabase(createTableSQL);
    }

    private void fillInStationData(){
//        BufferedReader in = new BufferedReader(new FileReader("<Filename>"));
//        String insertLocationSQL = "INSERT INTO Location (code, name) value (?,?)";
//        try(Connection connection = getDatabaseConnection();
//            PreparedStatement pStmt = connection.prepareStatement(insertLocationSQL)){
//            pStmt.setString(1, code);
//            pStmt.setString(2, name);
//            pStmt.executeUpdate()
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public ResultSet loadItemsFromDatabase() {
        return null;
    }

    @Override
    protected void pullDataToEventList() throws SQLException {

    }

    @Override
    protected void insertItemToDatabase() {

    }

    @Override
    protected void modifyItemInDatabase() {

    }

    @Override
    public void deleteItemInDatabase(int ID) {

    }
}

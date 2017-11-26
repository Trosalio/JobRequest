package server.persistence.obsoleted;


import java.io.File;
import java.io.IOException;

public class SQLiteConnector extends DBConnector {

    @Override
    public String setUpDriver() {
        String jdbc_url = "jdbc:sqlite:";
        File dbFile = new File("database.db");
        if (!dbFile.exists()) try {
            boolean success = dbFile.createNewFile();
            if (success) System.out.println("Successfully create new DB file");
            createTableIfNotExist();
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
                "jobID INTEGER NOT NULL," +
                "FOREIGN KEY(jobID) REFERENCES Job(ID));";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS Job" +
                "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "detailName TEXT NOT NULL," +
                "requester TEXT NOT NULL," +
                "typeOfMedia TEXT NOT NULL," +
                "quantity TEXT NOT NULL," +
                "fromDate TEXT NOT NULL," +
                "toDate TEXT NOT NULL," +
                "status TEXT NOT NULL);";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS Station" +
                "(code TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "PRIMARY KEY(code));";
        updateDatabase(createTableSQL);
        createTableSQL = "CREATE TABLE IF NOT EXISTS Stations_In_Job " +
                "(jobID INTEGER NOT NULL," +
                "stationCode TEXT NOT NULL," +
                "FOREIGN KEY(jobID) REFERENCES Job(ID)," +
                "FOREIGN KEY(stationCode) REFERENCES Station(code)," +
                "PRIMARY KEY(jobID,stationCode));";
        updateDatabase(createTableSQL);
    }
}

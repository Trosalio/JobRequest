package models.persistents;

import java.io.File;
import java.io.IOException;

/**
 * Project Name: MemoView
 */

public class SQLiteConnector extends DBConnector {

    public SQLiteConnector() {
        JDBC_DRIVER = "org.sqlite.JDBC";
        JDBC_URL = initJDBC_URL();
    }

    @Override
    protected String initJDBC_URL() {
        String jdbc_url = "jdbc:sqlite:";
        String homePath = System.getProperty("user.home");
        File dbFolder = new File(homePath + File.separator + "5029db");
        File dbFile = new File(dbFolder.getPath() + File.separator + "DateEvents.db");
        if (!dbFolder.exists() || !dbFile.exists()) try {
            boolean success = dbFolder.mkdir();
            if (success) success = dbFile.createNewFile();
            if (success) System.out.println("Successfully create new DB file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbc_url = jdbc_url + dbFile;
        return jdbc_url;
    }
}

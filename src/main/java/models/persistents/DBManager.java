package models.persistents;

import formatter.DateFormatter;
import models.MemoManager;

import java.time.format.DateTimeFormatter;

/**
 * Project Name: MemoView
 */

public class DBManager {

    private DateTimeFormatter dateTimeFormatter;
    private MemoManager memoManager;
    private DBConnector dbConnector;

    public DBManager(MemoManager memoManager){
        this.memoManager = memoManager;
        this.memoManager.setDatabase(this);
        this.dateTimeFormatter = (new DateFormatter()).getFormatter();
    }

    public void insertRecord(){

    }

    public void deleteRecord(){

    }

    public void modifyRecord(){

    }

    public void updateDatabase(){

    }

    public void pullDataToMemoList(){

    }

    public void setDatabaseConnector(DBConnector dbConnector){
        this.dbConnector = dbConnector;
    }
}

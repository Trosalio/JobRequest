package models.datastorage;

import Utilities.DateTimeFormatSingleton;
import models.MemoManager;

import java.time.format.DateTimeFormatter;

/**
 * Project Name: MemoView
 */

public class DBManager {

    private DateTimeFormatter dateTimeFormatter;
    private MemoManager memoManager;
    private DatabaseConnector dbConn;

    public DBManager(MemoManager memoManager){
        this.memoManager = memoManager;
        this.memoManager.setDatabase(this);
        this.dateTimeFormatter = DateTimeFormatSingleton.getInstance().getDateTimeFormat();
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

    public void setDatabaseConnector(DatabaseConnector dbConn){
        this.dbConn = dbConn;
    }
}

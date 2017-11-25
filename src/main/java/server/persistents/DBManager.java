package server.persistents;

import common.formatter.DateFormatter;
import client.controllers.AdvertiseManager;

import java.time.format.DateTimeFormatter;

/**
 * Project Name: MemoView
 */

public class DBManager {

    private DateTimeFormatter dateTimeFormatter;
    private AdvertiseManager advertiseManager;
    private DBConnector dbConnector;

    public DBManager(AdvertiseManager advertiseManager){
        this.advertiseManager = advertiseManager;
        this.advertiseManager.setDatabase(this);
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

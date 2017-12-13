package server.persistence;

import common.model.Advertise;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

//TODO implements queries code

public class AdvertiseDAO implements DAO {

    private final DataSource dataSource;
    private final AtomicInteger primaryKey = new AtomicInteger(0);

    @Autowired
    public AdvertiseDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void setup() {

    }

    private void createFileIfNotExisted(){

    }

    private void createTableIfNotExist() {
    }

    @Override
    public List<Advertise> loadAll() {
        return null;
    }

    @Override
    public void insert(Object object) {

    }

    @Override
    public void delete(Object object) {

    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void close() {

    }
}

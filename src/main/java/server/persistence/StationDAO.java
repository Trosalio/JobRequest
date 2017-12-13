package server.persistence;

import common.model.Job;
import common.model.Station;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

//TODO implements queries code

public class StationDAO implements DAO<Station> {

    private final DataSource dataSource;
    private final AtomicInteger primaryKey = new AtomicInteger(0);

    @Autowired
    public StationDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void setup() {

    }

    /**
     * This is a designated method for StationDAO
     * The method loads an event from a source according to the Station Code.
     */

    @Override
    public List<Station> loadAll() {
        return null;
    }

    @Override
    public void insert(Station station) {

    }

    @Override
    public void delete(Station station) {

    }

    @Override
    public void update(Station station) {

    }

    public List<Station> loadAll(Job job) {
        return null;
    }

    public void insert(Job job) {

    }

    public void delete(Job job) {

    }

    public void update(Job job) {

    }

    @Override
    public void close() {

    }
}

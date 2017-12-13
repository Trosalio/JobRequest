package server.persistence;

import common.model.Job;
import common.model.Station;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

//TODO implements queries code

public class StationDAO implements DAO<Station> {


    private DataSource dataSource;

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

    public List<Station> loadAll(Job job) {
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

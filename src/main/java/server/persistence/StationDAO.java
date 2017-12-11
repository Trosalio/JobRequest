package server.persistence;

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
    public Station load(String stationCode) {
        return null;
    }

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

    @Override
    public void close() {

    }
}

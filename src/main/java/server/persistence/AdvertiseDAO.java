package server.persistence;

import common.model.Advertise;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

//TODO implements queries code

public class AdvertiseDAO implements DAO {

    private DataSource dataSource;

    @Autowired
    public AdvertiseDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void setup() {

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

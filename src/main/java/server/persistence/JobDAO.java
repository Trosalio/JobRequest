package server.persistence;

import common.model.Job;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

//TODO implements queries code

public class JobDAO implements DAO<Job> {

    private DataSource dataSource;

    @Autowired
    public JobDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void setup() {

    }

    /**
     * This is a designated method for JobDAO
     * The method loads an event from a source according to the Job ID.
     */
    public Job load(int jobID) {
        return null;
    }

    @Override
    public List<Job> loadAll() {
        return null;
    }

    @Override
    public void insert(Job job) {

    }

    @Override
    public void delete(Job job) {

    }

    @Override
    public void update(Job job) {

    }

    @Override
    public void close() {

    }
}
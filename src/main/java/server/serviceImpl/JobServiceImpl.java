package server.serviceImpl;

import common.model.Job;
import common.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import server.persistence.JobDAO;

import java.util.List;

/**
 * ON PROGRESS!
 */

public class JobServiceImpl implements JobService {

    private final JobDAO dao;

    @Autowired
    public JobServiceImpl(JobDAO dao) {
        this.dao = dao;
    }


    @Override
    public Job loadJob(int jobID) {
        return dao.load(jobID);
    }

    @Override
    public List<Job> loadJobs() {
        return dao.loadAll();
    }

    @Override
    public void addJob(Job job) {
        dao.insert(job);
    }

    @Override
    public void updateJob(Job job) {
        dao.update(job);
    }

    @Override
    public void deleteJob(Job job) {
        dao.delete(job);
    }
}

package server.serviceImpl;

import common.model.Job;
import common.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import server.persistence.JobDAO;

import java.util.List;

public class JobServiceImpl implements JobService {

    private final JobDAO dao;

    @Autowired
    public JobServiceImpl(JobDAO dao) {
        this.dao = dao;
    }

    @Override
    public Job loadJob(int jobID) {
        System.out.println("loading a job");
        return dao.load(jobID);
    }

    @Override
    public List<Job> loadJobs() {
        System.out.println("loading jobs");
        return dao.loadAll();
    }

    @Override
    public void addJob(Job job) {
        System.out.println("inserting a job");
        dao.insert(job);
    }

    @Override
    public void updateJob(Job job) {
        System.out.println("updating a job");
        dao.update(job);
    }

    @Override
    public void deleteJob(Job job) {
        System.out.println("deleting a job");
        dao.delete(job);
    }
}

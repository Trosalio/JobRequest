package common.service;

import common.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {
    Job loadJob(int jobID);

    List<Job> loadJobs();

    void addJob(Job job);

    void updateJob(Job job);

    void deleteJob(Job job);
}

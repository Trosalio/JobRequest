package common.service;

import common.model.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    Job loadJob(String refNo);

    List<Job> loadJobs();

    int addJob(Job job);

    void updateJob(Job job);

    void deleteJob(Job job);
}

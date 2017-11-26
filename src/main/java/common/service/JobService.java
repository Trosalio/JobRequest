package common.service;

import common.model.Job;

import java.util.List;

/**
 * ON PROGRESS!
 */

public interface JobService {
    Job loadJob();

    List<Job> loadJobs();

    void addJob(Job job);

    void updateJob(Job job);

    void deleteJob(Job job);
}

package com.orange.job.management.scheduler;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.jobs.JobPerformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class JobScheduler {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobPerformer jobPerformer;

    private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);

    // create a scheduler that runs every minute (proposed duration between runs)
    @Scheduled(fixedRate = 60000)
    public void performJobs(){
        log.info("Starting Scheduler at: {}", LocalDateTime.now().withSecond(0).withNano(0));
        // get all jobs scheduled now to perform their jobs
        List<JobEntity> jobs = jobRepository.getCurrentJobs(LocalDateTime.now().withSecond(0).withNano(0));

        //insert the jobs in a priority queue to get get sorted according to their priority
        PriorityQueue<JobEntity> priorityJobs = prioritizeJobs(jobs);

        //perform each job sorted by priority
        priorityJobs.stream().forEach(jobPerformer::manageJob);
    }

    public PriorityQueue<JobEntity> prioritizeJobs(List<JobEntity> jobs){
        PriorityQueue<JobEntity> priorityJobs = new PriorityQueue<>();
        jobs.stream().forEach(priorityJobs::add);
        return priorityJobs;
    }
}

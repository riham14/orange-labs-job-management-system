package com.orange.job.management.components;

import com.orange.job.management.controller.JobController;
import com.orange.job.management.repositories.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Status;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class JobScheduler {

    @Autowired
    private JobRepository jobRepository;
    private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);

    @Scheduled(cron = "0 * * * * *")
    public void performJobs(){
        List<JobEntity> jobs = jobRepository.getCurrentJobs(LocalDateTime.now());

        PriorityQueue<JobEntity> priorityJobs = new PriorityQueue<>();

        jobs.stream().forEach(priorityJobs::add);

        priorityJobs.stream().forEach( job -> performJob(job));
    }

    private void performJob(JobEntity job){
        Status status = Status.RUNNING;
        try {
            log.info("Performing job with id {} and name {}", job.getId(), job.getName());
            if(((int)Math.random()) % 2 == 0){
                status = Status.SUCCEEDED;
            } else {
                status = Status.FAILED;
                throw new Exception();
            }
        }catch(Exception e){
            log.error("reverting job with id {} and name {}", job.getId(), job.getName());
        }
        job.setStatus(status.name());
        jobRepository.save(job);
    }
}

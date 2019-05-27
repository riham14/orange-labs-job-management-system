package com.orange.job.management.jobs;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class JobPerformer {

    @Autowired
    private JobRepository jobRepository;

    private static final Logger log = LoggerFactory.getLogger(JobPerformer.class);

    public void manageJob(JobEntity job) {
        // set the status of the current job to be performed to be RUNNING during execution and save it
        Status status = Status.RUNNING;
        job.setStatus(status);
        jobRepository.save(job);
        try {
            // added a proposed delay of 10 secs in order to see the RUNNING status in the db or on any UI
            Thread.sleep(10000);

            // perform the job action (just a proposed log in that case since the action itself is not important)
            performJobAction(job);
            job.setStatus(Status.SUCCEES);
        } catch (Exception e) {
            job.setStatus(Status.FAILED);
            rollbackFailedJob(job);
        }
        //save job after execution finish
        jobRepository.save(job);
    }

    public void performJobAction(JobEntity job) throws Exception {
        log.info("Performing job with id {} and name {}", job.getId(), job.getName());

        // since the action is not important, a random boolean is used to change the status of the job after finishing
        // to either by SUCCESS or FAILED
        Random rand = new Random();
        if (rand.nextBoolean()) {
        } else {
            throw new Exception();
        }
    }

    public void rollbackFailedJob(JobEntity job) {
        log.error("Reverting job with id {} and name {}", job.getId(), job.getName());
    }
}

package com.orange.job.management.util;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TestUtil {

    private long add = 1;

    @Autowired
    private JobRepository jobRepository;

    public void createJobEntitiesList(){
        int count = 10;
        while(count>0){
            createJobEntity(Priority.HIGH, LocalDateTime.now().plusMinutes(add).withSecond(0).withNano(0));
            count--;
        }
        add = 1;
    }

    public JobEntity createJobEntity(Priority priority, LocalDateTime scheduledTime) {
        JobEntity job = new JobEntity();
        job.setStatus(Status.QUEUED);
        job.setName("Job " + Long.toString(add));
        job.setScheduledTime(scheduledTime);
        add++;
        job.setPriority(priority);
        JobEntity createdJob = jobRepository.save(job);
        return createdJob;
    }

    public void deleteJobs(List<JobEntity> jobs){
        for(JobEntity job : jobs){
            jobRepository.delete(job);
        }
    }

    public void deleteJob(JobEntity job){
            jobRepository.delete(job);
    }

    public void deleteJobById(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}

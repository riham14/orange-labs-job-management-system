package com.orange.job.management.controller;

import com.orange.job.management.repositories.JobRepository;
import com.orange.job.management.dtos.JobObject;
import com.orange.job.management.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping(value="/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes =  MediaType.APPLICATION_JSON_VALUE)
    public Long createJob(@RequestParam(value = "job") JobObject job){
        JobEntity jobEntity = getJobEntity(job);
        JobEntity createdJob = jobRepository.save(jobEntity);
        return createdJob.getId();
    }

    @GetMapping(value="/status/{id}")
    @ResponseBody
    public String getJobStatus(@PathVariable(value = "id") Long jobId) throws Exception {
        Optional<JobEntity> job = jobRepository.findById(jobId);
        if(job.isPresent()) {
            System.out.println("============ job is present: " + job + "===================");
            return job.get().getStatus();
        } else {
            System.out.println("============ inside catch ==================");
            throw new Exception("No job exists with this Id");
        }
    }

    private JobEntity getJobEntity(@RequestParam("job") JobObject job) {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(job.name);
        jobEntity.setPriority(job.priority);
        jobEntity.setScheduledTime(job.scheduledTime);
        jobEntity.setStatus(job.status);
        return jobEntity;
    }
}

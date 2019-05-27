package com.orange.job.management.service;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public ResponseDTO createJob(JobCreationObject job) {
        JobEntity jobEntity = getJobEntity(job);
        JobEntity createdJob = jobRepository.save(jobEntity);
        return new ResponseDTO(createdJob.getId(), HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @Override
    public ResponseDTO getJobStatus(Long jobId) {
        ResponseDTO response = null;
        Optional<JobEntity> job = jobRepository.findById(jobId);
        if (job.isPresent()) {
            response = new ResponseDTO(job.get().getStatus().name(), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            response = new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.name(), Arrays.asList("No Job Exists with this Id"));
        }
        return response;
    }

    private JobEntity getJobEntity(@RequestParam("job") JobCreationObject job) {
        Priority priority = Priority.LOW;
        if (job.priority != null) {
            priority = Priority.valueOf(job.priority);
        }
        JobEntity jobEntity = new JobEntity();
        jobEntity.setName(job.name);
        jobEntity.setPriority(priority);
        jobEntity.setScheduledTime(job.scheduledTime);
        jobEntity.setStatus(Status.valueOf(job.status));
        return jobEntity;
    }
}

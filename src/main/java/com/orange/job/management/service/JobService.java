package com.orange.job.management.service;

import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.responses.JobResponseDTO;

public interface JobService {

    public JobResponseDTO createJob(JobCreationObject job);

    public JobResponseDTO getJobStatus(Long jobId);

    public JobResponseDTO getAllJobs();
}

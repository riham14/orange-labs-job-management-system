package com.orange.job.management.service;

import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.responses.ResponseDTO;

public interface JobService {

    public ResponseDTO createJob(JobCreationObject job);

    public ResponseDTO getJobStatus(Long jobId);
}

package com.orange.job.management.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Status;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponseDTO implements Serializable {

    private boolean success;
    private int code;
    private String status;
    private Long jobId;
    private String jobStatus;
    private List<String> errorMessages;
    private List<JobEntity> jobs;

    public JobResponseDTO(){}

    public JobResponseDTO(int code, String status, List<String> errorMessages){
        this.success = false;
        this.code = code;
        this.status = status;
        this.errorMessages = errorMessages;
    }

    public JobResponseDTO(Long jobId, int code, String status){
        this.success = true;
        this.jobId = jobId;
        this.code = code;
        this.status = status;
    }

    public JobResponseDTO(String jobStatus, int code, String status){
        this.success = true;
        this.jobStatus = jobStatus;
        this.code = code;
        this.status = status;
    }

    public JobResponseDTO(List<JobEntity> jobs, int code, String status){
        this.success = true;
        this.jobs = jobs;
        this.code = code;
        this.status = status;
    }
}

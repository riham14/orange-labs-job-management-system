package com.orange.job.management.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class JobObject {

    @JsonProperty(value = "name")
    @NotNull(message = "Please enter a job name")
    public String name;

    @JsonProperty(value = "priority")
    public String priority;

    @JsonProperty(value = "status")
    @NotNull(message = "Please enter a job status, ex: QUEUED")
    public String status;

    @JsonProperty(value = "scheduledTime")
    @NotNull(message = "Please enter the job scheduled time")
    public LocalDateTime scheduledTime;
}

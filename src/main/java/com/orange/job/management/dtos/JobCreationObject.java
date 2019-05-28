package com.orange.job.management.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;

import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Api(value = "Object used for Job Creation")
public class JobCreationObject {

    @JsonProperty(value = "name")
    @NotNull(message = "Please enter a job name")
    public String name;

    @JsonProperty(value = "priority")
    public String priority;

    @JsonProperty(value = "scheduledTime")
    @NotNull(message = "Please enter the job's scheduled time")
    public LocalDateTime scheduledTime;
}

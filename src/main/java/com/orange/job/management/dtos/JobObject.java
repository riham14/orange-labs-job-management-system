package com.orange.job.management.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class JobObject {

    @JsonProperty(value = "name", required = true)
    public String name;

    @JsonProperty(value = "priority", required = true)
    public String priority;

    @JsonProperty(value = "status", required = true)
    public String status;

    @JsonProperty(value = "scheduledTime", required = true)
    public LocalDateTime scheduledTime;
}

package com.orange.job.management.controller;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.dtos.JobObject;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.ResponseDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@Api(value = "Job Management System", tags = {"Job Management System Resource"})
@SwaggerDefinition(tags = {
        @Tag(name = "Job Management System Resource", description = "End Points for the Job Management System to create jobs and retrieve their status")
})
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @ApiOperation(value = "Create a new job")
    @ApiResponse(code = 200, message = "Job Successfully Created")
    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createJob(@Valid @RequestBody JobObject job) {
        JobEntity jobEntity = getJobEntity(job);
        JobEntity createdJob = jobRepository.save(jobEntity);
        ResponseDTO response = new ResponseDTO(createdJob.getId(), HttpStatus.OK.value(), HttpStatus.OK.name());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a job Status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Job Status Successfully Retrieved"),
            @ApiResponse(code = 406, message = "No Job Exists with this Id")
    })
    @GetMapping(value = "/status/{id}")
    @ResponseBody
    public ResponseEntity<?> getJobStatus(@PathVariable(value = "id") Long jobId) throws Exception {
        ResponseDTO response = null;
        Optional<JobEntity> job = jobRepository.findById(jobId);
        if (job.isPresent()) {
            response = new ResponseDTO(job.get().getStatus().name(), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            response = new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.name(), Arrays.asList("No Job Exists with this Id"));
        }

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    private JobEntity getJobEntity(@RequestParam("job") JobObject job) {
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

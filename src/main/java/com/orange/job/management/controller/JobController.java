package com.orange.job.management.controller;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.JobResponseDTO;
import com.orange.job.management.service.JobService;
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
@RequestMapping("/job")
@Api(value = "Job Management System", tags = {"Job Management System API"})
@SwaggerDefinition(tags = {
        @Tag(name = "Job Management System API", description = "End Points for the Job Management System to create jobs and retrieve their status")
})
public class JobController {

    @Autowired
    private JobService jobService;

    @ApiOperation(value = "Create a new job")
    @ApiResponse(code = 200, message = "Job Successfully Created")
    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createJob(@Valid @RequestBody JobCreationObject job) {
        JobResponseDTO response = jobService.createJob(job);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a job Status")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Job Status Successfully Retrieved"),
            @ApiResponse(code = 406, message = "No Job Exists with this Id")
    })
    @GetMapping(value = "/status/{id}")
    public ResponseEntity<?> getJobStatus(@PathVariable(value = "id") Long jobId) throws Exception {
        JobResponseDTO response = jobService.getJobStatus(jobId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ApiOperation(value = "Get all jobs")
    @ApiResponse(code = 200, message = "Jobs Successfully Retrieved")
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllJobs()  {
        JobResponseDTO response = jobService.getAllJobs();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

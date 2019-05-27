package com.orange.job.management.controller;

import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.dtos.JobCreationObject;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.ResponseDTO;
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
@RequestMapping("/jobs")
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
        ResponseDTO response = jobService.createJob(job);
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
        ResponseDTO response = jobService.getJobStatus(jobId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}

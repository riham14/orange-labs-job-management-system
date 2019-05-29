package com.orange.job.management.controller;

import com.orange.job.management.OrangeJobManagementSystemApplication;

import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.responses.JobResponseDTO;
import com.orange.job.management.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.properties")
public class JobControllerITest {
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private TestUtil testUtil;

    private static final String BASE_URL = "/job";

    private static HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    @Test
    public void createJobSuccessTest(){
        String userJson = "{ " +
                "\"name\": \"First Job\", " +
                "\"scheduledTime\":  \"2020-02-06T03:45:42.01\", " +
                "\"priority\":  \"HIGH\" " +
                "}";
        ResponseEntity<JobResponseDTO> response = template.postForEntity(BASE_URL + "/create", getHttpEntity(userJson), JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        testUtil.deleteJobById(response.getBody().getJobId());
    }

    @Test
    public void createJobFailureTest(){
        String userJson = "{ " +
                "\"name\": \"First Job\", " +
                "\"priority\":  \"HIGH\" " +
                "}";
        ResponseEntity<JobResponseDTO> response = template.postForEntity(BASE_URL + "/create", getHttpEntity(userJson), JobResponseDTO.class);

        Assert.assertFalse(response.getBody().isSuccess());
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void getJobStatusSuccessTest() throws Exception{

        //first, create a job entity and get its id, to get its status later
        Long jobId = testUtil.createJobEntity(Priority.HIGH, LocalDateTime.now().withSecond(0).withNano(0)).getId();
        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/status/" + jobId, JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        //delete the created job
        testUtil.deleteJobById(jobId);
    }

    @Test
    public void getJobStatusFailureTest() throws Exception{

        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/status/1", JobResponseDTO.class);

        Assert.assertFalse(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    @Test
    public void getAllJobsEmptyListTest(){
        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/all", JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().getJobs().isEmpty());
    }

    @Test
    public void getAllJobsTest(){
        //create some jobs first
        testUtil.createJobEntitiesList();

        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/all", JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(10, response.getBody().getJobs().size());

        //delete created jobs
        testUtil.deleteJobs(response.getBody().getJobs());
    }
}

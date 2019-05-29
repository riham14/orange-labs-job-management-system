package com.orange.job.management.controller;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.JobResponseDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.properties")
public class JobControllerITest {
    private long add = 1;
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private JobRepository jobRepository;

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

        jobRepository.deleteById(response.getBody().getJobId());
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
        Long jobId = createJobEntity();
        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/status/" + jobId, JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        //delete the created job
        jobRepository.deleteById(jobId);
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
        createJobEntitiesList();

        ResponseEntity<JobResponseDTO> response = template.getForEntity(BASE_URL + "/all", JobResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(10, response.getBody().getJobs().size());

        //delete created jobs
        for(JobEntity job : response.getBody().getJobs()){
            jobRepository.delete(job);
        }
    }

    public void createJobEntitiesList(){
        int count = 10;
        while(count>0){
            createJobEntity();
            count--;
        }
        add = 1;
    }

    private Long createJobEntity() {
        JobEntity job = new JobEntity();
        job.setStatus(Status.QUEUED);
        job.setName("Job " + Long.toString(add));
        job.setScheduledTime(LocalDateTime.now().plusMinutes(add).withSecond(0).withNano(0));
        add++;
        job.setPriority(Priority.HIGH);
        JobEntity createdJob = jobRepository.save(job);
        return createdJob.getId();
    }
}

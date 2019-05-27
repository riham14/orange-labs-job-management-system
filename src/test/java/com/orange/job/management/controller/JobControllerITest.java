package com.orange.job.management.controller;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.responses.ResponseDTO;
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

    //private MockMvc mvc;
    private long add = 1;
    @Autowired
    private TestRestTemplate template;

    @Autowired
    private JobRepository jobRepository;

    /*@Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

     */

    private static HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    /*
    @Test
    public void mockSuccessTest() throws Exception {
        MvcResult result = this.mvc.perform(get("/jobs/status/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        System.out.println("===========================" + result.getResponse().getStatus());
    }

    @Test
    public void getMockedJobStatusNotFoundJobTest() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/jobs/status/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

     */

    @Test
    public void createJobSuccessTest(){
        String userJson = "{ " +
                "\"name\": \"FirstJob\", " +
                "\"status\":  \"QUEUED\", " +
                "\"scheduledTime\":  \"2020-02-06T03:45:42.01\", " +
                "\"priority\":  \"HIGH\" " +
                "}";
        ResponseEntity<ResponseDTO> response = template.postForEntity("/jobs/create", getHttpEntity(userJson), ResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        jobRepository.deleteById(response.getBody().getJobId());
    }

    @Test
    public void createJobFailureTest(){
        String userJson = "{ " +
                "\"name\": \"FirstJob\", " +
                "\"status\":  \"QUEUED\", " +
                "\"priority\":  \"HIGH\" " +
                "}";
        ResponseEntity<ResponseDTO> response = template.postForEntity("/jobs/create", getHttpEntity(userJson), ResponseDTO.class);

        Assert.assertFalse(response.getBody().isSuccess());
        Assert.assertEquals(400, response.getStatusCodeValue());
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        System.out.println("==================" + response.getBody());
    }

    @Test
    public void getJobStatusSuccessTest() throws Exception{

        //first, create a job entity and get its id, to get its status later
        Long jobId = createJob();
        ResponseEntity<ResponseDTO> response = template.getForEntity("/jobs/status/" + jobId, ResponseDTO.class);

        Assert.assertTrue(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getJobStatusFailureTest() throws Exception{

        ResponseEntity<ResponseDTO> response = template.getForEntity("/jobs/status/1", ResponseDTO.class);

        Assert.assertFalse(response.getBody().isSuccess());
        Assert.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
    }

    private Long createJob() {
        JobEntity job = new JobEntity();
        job.setStatus(Status.QUEUED);
        job.setName("Job " + Long.toString(add));
        job.setScheduledTime(LocalDateTime.now().plusMinutes(add).withSecond(0).withNano(0));
        add++;
        job.setPriority(Priority.HIGH);
        JobEntity createdJob = jobRepository.save(job);
        return createdJob.getId();
    }

    @Test
    public void createEntitiesList(){
        int count = 10;
        while(count>0){
            createJob();
            count--;
        }
        add = 1;
    }


}

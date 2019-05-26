package com.orange.job.management.controller;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
@PropertySource("classpath:application.properties")
public class JobControllerTest {

    private MockMvc mvc;

    @Mock
    private JobController jobController;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    public void mockSuccessTest() throws Exception {
        MvcResult result = this.mvc.perform(get("/jobs/status/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        System.out.println("===========================" + result.getResponse().getStatus());
    }

    @Test
    public void createJobFailureTest(){
        HttpEntity<Object> response = new HttpEntity<>("");
    }

    @Test
    public void getMockedJobStatusNotFoundJobTest() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/jobs/status/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getJobStatusNotFoundJobTest() throws Exception{

        ResponseEntity<String> response = template.getForEntity("/jobs/status/1", String.class);

        System.out.println("============ body: " + response.getBody());
    }


}

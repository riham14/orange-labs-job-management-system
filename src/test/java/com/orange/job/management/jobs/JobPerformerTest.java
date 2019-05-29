package com.orange.job.management.jobs;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.properties")
public class JobPerformerTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobPerformer jobPerformer;

    @Test
    public void performJobSuccessTest() throws Exception {
        JobEntity job = createJob();
        Assert.assertTrue(job.getStatus().equals(Status.QUEUED));
        jobPerformer.manageJob(job);
        Assert.assertFalse(job.getStatus().equals(Status.QUEUED));
        jobRepository.delete(job);
    }

    private JobEntity createJob() {
        JobEntity job = new JobEntity();
        job.setStatus(Status.QUEUED);
        job.setName("Job Name");
        job.setScheduledTime(LocalDateTime.now().withSecond(0).withNano(0));
        job.setPriority(Priority.HIGH);
        JobEntity createdJob = jobRepository.save(job);
        return createdJob;
    }
}

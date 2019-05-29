package com.orange.job.management.jobs;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.properties")
public class JobPerformerTest {
    @Autowired
    private TestUtil testUtil;

    @Autowired
    private JobPerformer jobPerformer;

    @Test
    public void performJobSuccessTest() {
        JobEntity job = testUtil.createJobEntity(Priority.LOW, LocalDateTime.now().withSecond(0).withNano(0));
        Assert.assertTrue(job.getStatus().equals(Status.QUEUED));
        jobPerformer.manageJob(job);
        Assert.assertFalse(job.getStatus().equals(Status.QUEUED));
        testUtil.deleteJob(job);
    }
}

package com.orange.job.management.scheduler;

import com.orange.job.management.OrangeJobManagementSystemApplication;
import com.orange.job.management.dao.JobRepository;
import com.orange.job.management.entities.JobEntity;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
import com.orange.job.management.jobs.JobPerformer;
import com.orange.job.management.util.TestUtil;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrangeJobManagementSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application.properties")
public class JobSchedulerTest {

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private JobScheduler jobScheduler;

    @Test
    public void prioritizeJobSuccessTest() throws Exception {
        // add jobs with different priorities and make sure they get prioritized correctly
        List<JobEntity> jobs = new ArrayList<>();
        LocalDateTime scheduledTime = LocalDateTime.now().plusMinutes(1).withSecond(0).withNano(0);
        // create job with low priority
        JobEntity job = testUtil.createJobEntity(Priority.LOW, scheduledTime);
        Long jobId1 = job.getId();
        jobs.add(job);

        job = testUtil.createJobEntity(Priority.HIGH, scheduledTime);
        Long jobId2 = job.getId();
        jobs.add(job);

        PriorityQueue<JobEntity> priorityJobs = jobScheduler.prioritizeJobs(jobs);

        Assert.assertEquals(Priority.HIGH, priorityJobs.peek().getPriority());

        //delete created jobs
        testUtil.deleteJobById(jobId1);
        testUtil.deleteJobById(jobId2);

    }
}

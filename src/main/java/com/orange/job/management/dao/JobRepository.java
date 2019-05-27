package com.orange.job.management.dao;

import com.orange.job.management.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("select jobs from JobEntity jobs where jobs.scheduledTime = ?1")
    List<JobEntity> getCurrentJobs(LocalDateTime time);
}

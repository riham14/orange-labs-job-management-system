package com.orange.job.management.repositories;

import com.orange.job.management.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

    @Query("select jobs from JobEntity jobs where jobs.scheduledTime =: time")
    List<JobEntity> getCurrentJobs(@Param("time") LocalDateTime time);

}

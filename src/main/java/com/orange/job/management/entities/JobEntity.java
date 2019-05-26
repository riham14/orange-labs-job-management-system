package com.orange.job.management.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
public class JobEntity implements Comparable<JobEntity>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    @Column(name = "scheduledTime")
    private LocalDateTime scheduledTime;

    @Override
    public int compareTo(JobEntity o) {
        if(this.getScheduledTime().isBefore(o.getScheduledTime())){
            return -1;
        } else if(this.getScheduledTime().isAfter(o.getScheduledTime())){
            return 1;
        } else {
            return 0;
        }
    }
}

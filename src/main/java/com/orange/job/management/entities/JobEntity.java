package com.orange.job.management.entities;

import com.orange.job.management.utils.LocalDateTimeConverter;
import com.orange.job.management.enumerations.Priority;
import com.orange.job.management.enumerations.Status;
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
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "scheduledTime")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime scheduledTime;

    @Override
    public int compareTo(JobEntity o) {
        if(this.getPriority().ordinal() < o.getPriority().ordinal()){
            return -1;
        } else if(this.getPriority().ordinal() > o.getPriority().ordinal()){
            return 1;
        } else {
            return 0;
        }
    }
}

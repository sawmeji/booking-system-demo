package com.mks.bookingsystemdemo.entity;

import com.mks.bookingsystemdemo.dto.ClassScheduleDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private String country;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int requiredCredits;
    private int availableSlots;

    private int waitlistCount;

    public ClassSchedule() {
    }

    public ClassSchedule(ClassScheduleDto scheduleDto) {
        this.className = scheduleDto.getClassName();
        this.country = scheduleDto.getCountry();
        this.startTime = scheduleDto.getStartTime();
        this.endTime = scheduleDto.getEndTime();
        this.requiredCredits = scheduleDto.getRequiredCredits();
        this.availableSlots = scheduleDto.getAvailableSlots();
    }
}


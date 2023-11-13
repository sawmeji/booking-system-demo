package com.mks.bookingsystemdemo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClassScheduleDto {

    private String className;
    private String country;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int requiredCredits;
    private int availableSlots;

}

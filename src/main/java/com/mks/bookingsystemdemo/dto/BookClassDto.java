package com.mks.bookingsystemdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookClassDto {

    private Long userId;
    private Long packageId;
    private Long classScheduleId;

    public BookClassDto() {
    }

    public BookClassDto(Long userId, Long packageId, Long classScheduleId) {
        this.userId = userId;
        this.packageId = packageId;
        this.classScheduleId = classScheduleId;
    }
}

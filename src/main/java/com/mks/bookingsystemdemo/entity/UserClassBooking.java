package com.mks.bookingsystemdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserClassBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "classId", nullable = false)
    private ClassSchedule classSchedule;

    private LocalDateTime bookingTime;

    private boolean waitlist;


    public UserClassBooking() {
    }

    public UserClassBooking(Long userId, ClassSchedule classSchedule, LocalDateTime bookingTime) {
        this.userId = userId;
        this.classSchedule = classSchedule;
        this.bookingTime = bookingTime;
    }
}

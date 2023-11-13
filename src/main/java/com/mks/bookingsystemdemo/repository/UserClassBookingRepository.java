package com.mks.bookingsystemdemo.repository;

import com.mks.bookingsystemdemo.entity.UserClassBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserClassBookingRepository extends JpaRepository<UserClassBooking,Long> {
    List<UserClassBooking> findByUserId(Long userId);

    boolean existsByUserIdAndClassSchedule_Id(Long userId, Long classId);

    List<UserClassBooking> findByClassSchedule_Id(Long classId);

    boolean existsByUserIdAndClassSchedule_IdAndWaitlist (Long userId, Long classId, boolean waitlist);


    List<UserClassBooking> findByClassSchedule_IdAndWaitlist (Long classId, Boolean wlist);



}

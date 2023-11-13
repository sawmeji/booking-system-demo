package com.mks.bookingsystemdemo.repository;

import com.mks.bookingsystemdemo.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByCountry(String country);

    Optional<ClassSchedule> findClassScheduleById(Long id);

    ClassSchedule findByIdOrderByClassName(Long id);

    @Query("SELECT c FROM ClassSchedule c WHERE c.availableSlots = 0")
    List<ClassSchedule> findFullClasses();
}


package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.dto.ClassScheduleDto;
import com.mks.bookingsystemdemo.entity.ClassSchedule;
import com.mks.bookingsystemdemo.service.ClassScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-schedules")
public class ClassScheduleController {
    @Autowired
    private ClassScheduleService classScheduleService;

    @GetMapping("/country/{country}")
    public ResponseEntity<List<ClassSchedule>> getClassSchedulesByCountry(@PathVariable String country) {
        List<ClassSchedule> classSchedules = classScheduleService.getClassSchedulesByCountry(country);
        return new ResponseEntity<>(classSchedules, HttpStatus.OK);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassSchedule> getClassScheduleById(@PathVariable Long classId) {
        ClassSchedule classSchedule = classScheduleService.getClassScheduleById(classId);
        if (classSchedule != null) {
            return new ResponseEntity<>(classSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClassSchedule(@RequestBody ClassScheduleDto classSchedule) {
        classScheduleService.createClassSchedule(classSchedule);
        return new ResponseEntity<>("Class schedule created successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/update/{classId}")
    public ResponseEntity<String> updateClassSchedule(@PathVariable Long classId, @RequestBody ClassSchedule updatedClassSchedule) {
        boolean success = classScheduleService.updateClassSchedule(classId, updatedClassSchedule);
        if (success) {
            return new ResponseEntity<>("Class schedule updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update class schedule. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<String> deleteClassSchedule(@PathVariable Long classId) {
        boolean success = classScheduleService.deleteClassSchedule(classId);
        if (success) {
            return new ResponseEntity<>("Class schedule deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete class schedule. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

}
